/**
 * ***************************************************************
 * 文件名称：app.js
 * 摘   要：初始化js
 * 作   者：
 * 创建时间：
 * 版权所有：
 * 开发记录：
 * ****************************************************************
 */
var App = function () {

    // IE mode
    var isRTL = false;
    var isIE8 = false;
    var isIE9 = false;
    var isIE10 = false;

    
    var responsiveHandlers = [];

    // To get the correct viewport width based on  http://andylangton.co.uk/articles/javascript/get-viewport-size-javascript/
    var _getViewPort = function () {
        var e = window, a = 'inner';
        if (!('innerWidth' in window)) {
            a = 'client';
            e = document.documentElement || document.body;
        }
        return {
            width: e[a + 'Width'],
            height: e[a + 'Height']
        }
    }

    // initializes main settings
    var handleInit = function () {

        if ($('body').css('direction') === 'rtl') {
            isRTL = true;
        }

        isIE8 = !! navigator.userAgent.match(/MSIE 8.0/);
        isIE9 = !! navigator.userAgent.match(/MSIE 9.0/);
        isIE10 = !! navigator.userAgent.match(/MSIE 10.0/);

        if (isIE10) {
            jQuery('html').addClass('ie10'); // detect IE10 version
        }
        
        if (isIE10 || isIE9 || isIE8) {
            jQuery('html').addClass('ie'); // detect IE10 version
        }

        /*
          Virtual keyboards:
          Also, note that if you're using inputs in your modal – iOS has a rendering bug which doesn't 
          update the position of fixed elements when the virtual keyboard is triggered  
        */
        // var deviceAgent = navigator.userAgent.toLowerCase();
        // if (deviceAgent.match(/(iphone|ipod|ipad)/)) {
        //     $(document).on('focus', 'input, textarea', function () {
        //         $('.page-header').hide();
        //         $('.page-footer').hide();
        //     });
        //     $(document).on('blur', 'input, textarea', function () {
        //         $('.page-header').show();
        //         $('.page-footer').show();
        //     });
        // }
    }

    // reinitialize the laypot on window resize
    // var handleResponsive = function () {
    //     // handleSidebarState();
    //     handleSidebarAndContentHeight();
    //     handleFixedSidebar();
    //     // runResponsiveHandlers();
    // }

    // initialize the layout on page load
    var handleResponsiveOnInit = function () {
        // handleSidebarState();
        handleSidebarAndContentHeight();
    }

    // handle the layout reinitialization on window resize
    // var handleResponsiveOnResize = function () {
    //     var resize;
    //     if (isIE8) {
    //         var currheight;
    //         $(window).resize(function () {
    //             if (currheight == document.documentElement.clientHeight) {
    //                 return; //quite event since only body resized not window.
    //             }
    //             if (resize) {
    //                 clearTimeout(resize);
    //             }
    //             resize = setTimeout(function () {
    //                 handleResponsive();
    //             }, 50); // wait 50ms until window resize finishes.                
    //             currheight = document.documentElement.clientHeight; // store last body client height
    //         });
    //     } else {
    //         $(window).resize(function () {
    //             if (resize) {
    //                 clearTimeout(resize);
    //             }
    //             resize = setTimeout(function () {
    //                 handleResponsive();
    //             }, 50); // wait 50ms until window resize finishes.
    //         });
    //     }
    // }

    //* BEGIN:CORE HANDLERS *//
    // this function handles responsive layout on screen size resize or mobile device rotate.

    // Set proper height for sidebar and content. The content and sidebar height must be synced always.
    var handleSidebarAndContentHeight = function () {
        var content = $('.page-contenter');
        // var sidebar = $('.page-sidebar');
        // var body = $('body');
        var height;

        // if (body.hasClass("page-footer-fixed") === true && body.hasClass("page-sidebar-fixed") === false) {
        //     var available_height = $(window).height() - $('.footer').outerHeight() - $('.header').outerHeight();
        //     if (content.height() < available_height) {
        //         content.attr('style', 'min-height:' + available_height + 'px !important');
        //     }
        // } else {
            // if (body.hasClass('page-sidebar-fixed')) {
            //     height = _calculateFixedSidebarViewportHeight();
            // } else {
                // height = sidebar.height() + 20;
                // var headerHeight = $('.header').outerHeight();
                // var footerHeight = $('.footer').outerHeight();                
                // if ($(window).width() > 1024) {
                    height = $(window).height();
                // }
            // }            
            // if (height >= content.height()) {
                content.attr('style', 'min-height:' + height + 'px !important');
                $('.page-content').attr('style', 'min-height:' + height + 'px !important');
            // }
        // }
    }


    // Handles portlet tools & actions
    // var handlePortletTools = function () {
    //     jQuery('body').on('click', '.portlet > .portlet-title > .tools > a.remove', function (e) {
    //         e.preventDefault();
    //         jQuery(this).closest(".portlet").remove();
    //     });

    //     jQuery('body').on('click', '.portlet > .portlet-title > .tools > a.reload', function (e) {
    //         e.preventDefault();
    //         var el = jQuery(this).closest(".portlet").children(".portlet-body");
    //         var url = jQuery(this).attr("data-url");
    //         var error = $(this).attr("data-error-display");
    //         if (url) {
    //             App.blockUI({target: el, iconOnly: true});
    //             $.ajax({
    //                 type: "GET",
    //                 cache: false,
    //                 url: url,
    //                 dataType: "html",
    //                 success: function(res) 
    //                 {                        
    //                     App.unblockUI(el);
    //                     el.html(res);
    //                 },
    //                 error: function(xhr, ajaxOptions, thrownError)
    //                 {
    //                     App.unblockUI(el);
    //                     var msg = 'Error on reloading the content. Please check your connection and try again.';
    //                     if (error == "toastr" && toastr) {
    //                         toastr.error(msg);
    //                     } else if (error == "notific8" && $.notific8) {
    //                         $.notific8('zindex', 11500);
    //                         $.notific8(msg, {theme: 'ruby', life: 3000});
    //                     } else {
    //                         alert(msg);
    //                     }
    //                 }
    //             });
    //         } else {
    //             // for demo purpose
    //             App.blockUI({target: el, iconOnly: true});
    //             window.setTimeout(function () {
    //                 App.unblockUI(el);
    //             }, 1000);
    //         }            
    //     });

    //     // load ajax data on page init
    //     $('.portlet .portlet-title a.reload[data-load="true"]').click();

    //     jQuery('body').on('click', '.portlet > .portlet-title > .tools > .collapse, .portlet .portlet-title > .tools > .expand', function (e) {
    //         e.preventDefault();
    //         var el = jQuery(this).closest(".portlet").children(".portlet-body");
    //         if (jQuery(this).hasClass("collapse")) {
    //             jQuery(this).removeClass("collapse").addClass("expand");
    //             el.slideUp(200);
    //         } else {
    //             jQuery(this).removeClass("expand").addClass("collapse");
    //             el.slideDown(200);
    //         }
    //     });
    // }

    // Handles custom checkboxes & radios using jQuery Uniform plugin
    // var handleUniform = function () {
    //     if (!jQuery().uniform) {
    //         return;
    //     }
    //     var test = $("input[type=checkbox]:not(.toggle, .make-switch), input[type=radio]:not(.toggle, .star, .make-switch)");
    //     if (test.size() > 0) {
    //         test.each(function () {
    //             if ($(this).parents(".checker").size() == 0) {
    //                 $(this).show();
    //                 $(this).uniform();
    //             }
    //         });
    //     }
    // }

    // var handleBootstrapSwitch = function () {
    //     if (!jQuery().bootstrapSwitch) {
    //         return;
    //     }
    //     $('.make-switch').bootstrapSwitch();
    // }

    // Handles Bootstrap Accordions.
    // var handleAccordions = function () {
    //     jQuery('body').on('shown.bs.collapse', '.accordion.scrollable', function (e) {
    //         App.scrollTo($(e.target));
    //     });
    // }

    // Handles Bootstrap Tabs.
    // var handleTabs = function () {
    //     // fix content height on tab click
    //     $('body').on('shown.bs.tab', '.nav.nav-tabs', function () {
    //         handleSidebarAndContentHeight();
    //     });

    //     //activate tab if tab id provided in the URL
    //     if (location.hash) {
    //         var tabid = location.hash.substr(1);
    //         $('a[href="#' + tabid + '"]').parents('.tab-pane:hidden').each(function(){
    //             var tabid = $(this).attr("id");
    //             $('a[href="#' + tabid + '"]').click();    
    //         });            
    //         $('a[href="#' + tabid + '"]').click();
    //     }
    // }

    // Handles Bootstrap Modals.
    // var handleModals = function () {
    //     // fix stackable modal issue: when 2 or more modals opened, closing one of modal will remove .modal-open class. 
    //     $('body').on('hide.bs.modal', function () {
    //        if ($('.modal:visible').size() > 1 && $('html').hasClass('modal-open') == false) {
    //           $('html').addClass('modal-open');
    //        } else if ($('.modal:visible').size() <= 1) {
    //           $('html').removeClass('modal-open');
    //        }
    //     });
            
    //     $('body').on('show.bs.modal', '.modal', function () {
    //         if ($(this).hasClass("modal-scroll")) {
    //             $('body').addClass("modal-open-noscroll");
    //         } 
    //     });

    //     $('body').on('hide.bs.modal', '.modal', function () {
    //         $('body').removeClass("modal-open-noscroll");
    //     });
    // }

    // Handles Bootstrap Tooltips.
    // var handleTooltips = function () {
    //    jQuery('.tooltips').tooltip();
    // }

    // Handles Bootstrap Dropdowns
    // var handleDropdowns = function () {
    //     /*
    //       Hold dropdown on click  
    //     */
    //     $('body').on('click', '.dropdown-menu.hold-on-click', function (e) {
    //         e.stopPropagation();
    //     });
    // }

    // Handle Hower Dropdowns
    // var handleDropdownHover = function () {
    //     $('[data-hover="dropdown"]').dropdownHover();
    // }

    // var handleAlerts = function () {
    //     $('body').on('click', '[data-close="alert"]', function(e){
    //         $(this).parent('.alert').hide();
    //         e.preventDefault();
    //     });
    // }

    // Handles Bootstrap Popovers

    // last popep popover
    // var lastPopedPopover;

    // var handlePopovers = function () {
    //     jQuery('.popovers').popover();

    //     // close last poped popover

    //     $(document).on('click.bs.popover.data-api', function (e) {
    //         if (lastPopedPopover) {
    //             lastPopedPopover.popover('hide');
    //         }
    //     });
    // }

    // Handles scrollable contents using jQuery SlimScroll plugin.
    // var handleScrollers = function () {
    //     $('.scroller').each(function () {
    //         var height;
    //         if ($(this).attr("data-height")) {
    //             height = $(this).attr("data-height");
    //         } else {
    //             height = $(this).css('height');
    //         }
    //         $(this).slimScroll({
    //             allowPageScroll: true, // allow page scroll when the element scroll is ended
    //             size: '7px',
    //             color: ($(this).attr("data-handle-color")  ? $(this).attr("data-handle-color") : '#bbb'),
    //             railColor: ($(this).attr("data-rail-color")  ? $(this).attr("data-rail-color") : '#eaeaea'),
    //             position: isRTL ? 'left' : 'right',
    //             height: height,
    //             alwaysVisible: ($(this).attr("data-always-visible") == "1" ? true : false),
    //             railVisible: ($(this).attr("data-rail-visible") == "1" ? true : false),
    //             disableFadeOut: true
    //         });
    //     });
    // }

    // Handles Image Preview using jQuery Fancybox plugin
    // var handleFancybox = function () {
    //     if (!jQuery.fancybox) {
    //         return;
    //     }

    //     if (jQuery(".fancybox-button").size() > 0) {
    //         jQuery(".fancybox-button").fancybox({
    //             groupAttr: 'data-rel',
    //             prevEffect: 'none',
    //             nextEffect: 'none',
    //             closeBtn: true,
    //             helpers: {
    //                 title: {
    //                     type: 'inside'
    //                 }
    //             }
    //         });
    //     }
    // }

    // Fix input placeholder issue for IE8 and IE9
    var handleFixInputPlaceholderForIE = function () {
        //fix html5 placeholder attribute for ie7 & ie8
        if (isIE8 || isIE9) { // ie8 & ie9
            // this is html5 placeholder fix for inputs, inputs with placeholder-no-fix class will be skipped(e.g: we need this for password fields)
            jQuery('input[placeholder]:not(.placeholder-no-fix), textarea[placeholder]:not(.placeholder-no-fix)').each(function () {

                var input = jQuery(this);

                if (input.val() == '' && input.attr("placeholder") != '') {
                    input.addClass("placeholder").val(input.attr('placeholder'));
                }

                input.focus(function () {
                    if (input.val() == input.attr('placeholder')) {
                        input.val('');
                    }
                });

                input.blur(function () {
                    if (input.val() == '' || input.val() == input.attr('placeholder')) {
                        input.val(input.attr('placeholder'));
                    }
                });
            });
        }
    }

    // Handle Select2 Dropdowns
    // var handleSelect2 = function() {
    //     if (jQuery().select2) {
    //         $('.select2me').select2({
    //             placeholder: "Select",
    //             allowClear: true
    //         });
    //     }
    // }


    //* END:CORE HANDLERS *//

    return {

        //main function to initiate the theme
        init: function () {

            //IMPORTANT!!!: Do not modify the core handlers call order.

            //core handlers
            // handleInit(); // initialize core variables
            // handleResponsiveOnResize(); // set and handle responsive    
            // handleUniform(); // hanfle custom radio & checkboxes
            // handleBootstrapSwitch(); // handle bootstrap switch plugin
            // handleScrollers(); // handles slim scrolling contents 
            handleResponsiveOnInit(); // handler responsive elements on page load

            //layout handlers
            //handleFixedSidebar(); // handles fixed sidebar menu
            //handleFixedSidebarHoverable(); // handles fixed sidebar on hover effect 
            //handleSidebarMenu(); // handles main menu
            //handleHorizontalMenu(); // handles horizontal menu
            //handleSidebarToggler(); // handles sidebar hide/show            
            handleFixInputPlaceholderForIE(); // fixes/enables html5 placeholder attribute for IE9, IE8
            //handleGoTop(); //handles scroll to top functionality in the footer
            //handleTheme(); // handles style customer tool

            //ui component handlers
            // handleFancybox() // handle fancy box
            // handleSelect2(); // handle custom Select2 dropdowns
            // handlePortletTools(); // handles portlet action bar functionality(refresh, configure, toggle, remove)
            // handleAlerts(); //handle closabled alerts
            // handleDropdowns(); // handle dropdowns
            // handleTabs(); // handle tabs
            // handleTooltips(); // handle bootstrap tooltips
            // handlePopovers(); // handles bootstrap popovers
            // handleAccordions(); //handles accordions 
            // handleModals(); // handle modals
            // handleFullScreenMode(); // handles full screen
        },

        //main function to initiate core javascript after ajax complete
        // initAjax: function () {
        //     handleScrollers(); // handles slim scrolling contents 
        //     handleSelect2(); // handle custom Select2 dropdowns
        //     handleDropdowns(); // handle dropdowns
        //     handleTooltips(); // handle bootstrap tooltips
        //     handlePopovers(); // handles bootstrap popovers
        //     handleAccordions(); //handles accordions 
        //     handleUniform(); // hanfle custom radio & checkboxes     
        //     handleBootstrapSwitch(); // handle bootstrap switch plugin
        //     handleDropdownHover() // handles dropdown hover       
        // },

        //public function to fix the sidebar and content height accordingly
        // fixContentHeight: function () {
        //     handleSidebarAndContentHeight();
        // },

        //public function to remember last opened popover that needs to be closed on click
        // setLastPopedPopover: function (el) {
        //     lastPopedPopover = el;
        // },

        //public function to add callback a function which will be called on window resize
        // addResponsiveHandler: function (func) {
        //     responsiveHandlers.push(func);
        // },

        // useful function to make equal height for contacts stand side by side
        // setEqualHeight: function (els) {
        //     var tallestEl = 0;
        //     els = jQuery(els);
        //     els.each(function () {
        //         var currentHeight = $(this).height();
        //         if (currentHeight > tallestEl) {
        //             tallestColumn = currentHeight;
        //         }
        //     });
        //     els.height(tallestEl);
        // },

        // wrapper function to scroll(focus) to an element
        // scrollTo: function (el, offeset) {
        //     var pos = (el && el.size() > 0) ? el.offset().top : 0;

        //     if (el) {
        //         if ($('body').hasClass('page-header-fixed')) {
        //             pos = pos - $('.header').height(); 
        //         }            
        //         pos = pos + (offeset ? offeset : -1 * el.height());
        //     }

        //     jQuery('html,body').animate({
        //         scrollTop: pos
        //     }, 'slow');
        // },

        // function to scroll to the top
        // scrollTop: function () {
        //     App.scrollTo();
        // },

        // wrapper function to  block element(indicate loading)
        // blockUI: function (options) {
        //     var options = $.extend(true, {}, options);
        //     var html = '';
        //     if (options.iconOnly) {
        //         html = '<div class="loading-message ' + (options.boxed ? 'loading-message-boxed' : '')+'"><img style="" src="./assets/img/loading-spinner-grey.gif" align=""></div>';
        //     } else if (options.textOnly) {
        //         html = '<div class="loading-message ' + (options.boxed ? 'loading-message-boxed' : '')+'"><span>&nbsp;&nbsp;' + (options.message ? options.message : 'LOADING...') + '</span></div>';
        //     } else {    
        //         html = '<div class="loading-message ' + (options.boxed ? 'loading-message-boxed' : '')+'"><img style="" src="./assets/img/loading-spinner-grey.gif" align=""><span>&nbsp;&nbsp;' + (options.message ? options.message : 'LOADING...') + '</span></div>';
        //     }

        //     if (options.target) { // element blocking
        //         var el = jQuery(options.target);
        //         if (el.height() <= ($(window).height())) {
        //             options.cenrerY = true;
        //         }            
        //         el.block({
        //             message: html,
        //             baseZ: options.zIndex ? options.zIndex : 1000,
        //             centerY: options.cenrerY != undefined ? options.cenrerY : false,
        //             css: {
        //                 top: '10%',
        //                 border: '0',
        //                 padding: '0',
        //                 backgroundColor: 'none'
        //             },
        //             overlayCSS: {
        //                 backgroundColor: options.overlayColor ? options.overlayColor : '#000',
        //                 opacity: options.boxed ? 0.05 : 0.1, 
        //                 cursor: 'wait'
        //             }
        //         });
        //     } else { // page blocking
        //         $.blockUI({
        //             message: html,
        //             baseZ: options.zIndex ? options.zIndex : 1000,
        //             css: {
        //                 border: '0',
        //                 padding: '0',
        //                 backgroundColor: 'none'
        //             },
        //             overlayCSS: {
        //                 backgroundColor: options.overlayColor ? options.overlayColor : '#000',
        //                 opacity: options.boxed ? 0.05 : 0.1,
        //                 cursor: 'wait'
        //             }
        //         });
        //     }            
        // },

        // wrapper function to  un-block element(finish loading)
        // unblockUI: function (target) {
        //     if (target) {
        //         jQuery(target).unblock({
        //             onUnblock: function () {
        //                 jQuery(target).css('position', '');
        //                 jQuery(target).css('zoom', '');
        //             }
        //         });
        //     } else {
        //         $.unblockUI();
        //     }
        // },

        // startPageLoading: function(message) {
        //     $('.page-loading').remove();
        //     $('body').append('<div class="page-loading"><img src="assets/img/loading-spinner-grey.gif"/>&nbsp;&nbsp;<span>' + (message ? message : 'Loading...') + '</span></div>');
        // },

        // stopPageLoading: function() {
        //     $('.page-loading').remove();
        // },

        // initializes uniform elements
        // initUniform: function (els) {
        //     if (els) {
        //         jQuery(els).each(function () {
        //             if ($(this).parents(".checker").size() == 0) {
        //                 $(this).show();
        //                 $(this).uniform();
        //             }
        //         });
        //     } else {
        //         handleUniform();
        //     }
        // },

        //wrapper function to update/sync jquery uniform checkbox & radios
        // updateUniform: function (els) {
        //     $.uniform.update(els); // update the uniform checkbox & radios UI after the actual input control state changed
        // },

        //public function to initialize the fancybox plugin
        // initFancybox: function () {
        //     handleFancybox();
        // },

        //public helper function to get actual input value(used in IE9 and IE8 due to placeholder attribute not supported)
        getActualVal: function (el) {
            var el = jQuery(el);
            if (el.val() === el.attr("placeholder")) {
                return "";
            }
            return el.val();
        },

        //public function to get a paremeter by name from URL
        // getURLParameter: function (paramName) {
        //     var searchString = window.location.search.substring(1),
        //         i, val, params = searchString.split("&");

        //     for (i = 0; i < params.length; i++) {
        //         val = params[i].split("=");
        //         if (val[0] == paramName) {
        //             return unescape(val[1]);
        //         }
        //     }
        //     return null;
        // },

        // check for device touch support
        // isTouchDevice: function () {
        //     try {
        //         document.createEvent("TouchEvent");
        //         return true;
        //     } catch (e) {
        //         return false;
        //     }
        // },

        // getUniqueID: function(prefix) {
        //     return 'prefix_' + Math.floor(Math.random() * (new Date()).getTime());
        // },

        // alert: function(options) {

        //     options = $.extend(true, {
        //         container: "", // alerts parent container(by default placed after the page breadcrumbs)
        //         place: "append", // append or prepent in container 
        //         type: 'success',  // alert's type
        //         message: "",  // alert's message
        //         close: true, // make alert closable
        //         reset: true, // close all previouse alerts first
        //         focus: true, // auto scroll to the alert after shown
        //         closeInSeconds: 0, // auto close after defined seconds
        //         icon: "" // put icon before the message
        //     }, options);

        //     var id = App.getUniqueID("app_alert");

        //     var html = '<div id="'+id+'" class="app-alerts alert alert-'+options.type+' fade in">' + (options.close ? '<button type="button" class="close" data-dismiss="alert" aria-hidden="true"></button>' : '' ) + (options.icon != "" ? '<i class="fa-lg fa fa-'+options.icon + '"></i>  ' : '') + options.message+'</div>'

        //     if (options.reset) {0
        //         $('.app-alerts').remove();
        //     }

        //     if (!options.container) {
        //         $('.page-breadcrumb').after(html);
        //     } else {
        //         if (options.place == "append") {
        //             $(options.container).append(html);
        //         } else {
        //             $(options.container).prepend(html);
        //         }
        //     }

        //     if (options.focus) {
        //         App.scrollTo($('#' + id));
        //     }

        //     if (options.closeInSeconds > 0) {
        //         setTimeout(function(){
        //             $('#' + id).remove();
        //         }, options.closeInSeconds * 1000);
        //     }
        // },

        // check IE8 mode
        isIE8: function () {
            return isIE8;
        },

        // check IE9 mode
        isIE9: function () {
            return isIE9;
        },

        //check RTL mode
        // isRTL: function () {
        //     return isRTL;
        // },

        // get layout color code by color name
        // getLayoutColorCode: function (name) {
        //     if (layoutColorCodes[name]) {
        //         return layoutColorCodes[name];
        //     } else {
        //         return '';
        //     }
        // }

    };

}();