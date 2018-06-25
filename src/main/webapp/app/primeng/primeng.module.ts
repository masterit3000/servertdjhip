
import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';

import { ServertdjhipButtonDemoModule } from './buttons/button/buttondemo.module';
import { ServertdjhipSplitbuttonDemoModule } from './buttons/splitbutton/splitbuttondemo.module';

import { ServertdjhipDialogDemoModule } from './overlay/dialog/dialogdemo.module';
import { ServertdjhipConfirmDialogDemoModule } from './overlay/confirmdialog/confirmdialogdemo.module';
import { ServertdjhipLightboxDemoModule } from './overlay/lightbox/lightboxdemo.module';
import { ServertdjhipTooltipDemoModule } from './overlay/tooltip/tooltipdemo.module';
import { ServertdjhipOverlayPanelDemoModule } from './overlay/overlaypanel/overlaypaneldemo.module';
import { ServertdjhipSideBarDemoModule } from './overlay/sidebar/sidebardemo.module';

import { ServertdjhipKeyFilterDemoModule } from './inputs/keyfilter/keyfilterdemo.module';
import { ServertdjhipInputTextDemoModule } from './inputs/inputtext/inputtextdemo.module';
import { ServertdjhipInputTextAreaDemoModule } from './inputs/inputtextarea/inputtextareademo.module';
import { ServertdjhipInputGroupDemoModule } from './inputs/inputgroup/inputgroupdemo.module';
import { ServertdjhipCalendarDemoModule } from './inputs/calendar/calendardemo.module';
import { ServertdjhipCheckboxDemoModule } from './inputs/checkbox/checkboxdemo.module';
import { ServertdjhipChipsDemoModule } from './inputs/chips/chipsdemo.module';
import { ServertdjhipColorPickerDemoModule } from './inputs/colorpicker/colorpickerdemo.module';
import { ServertdjhipInputMaskDemoModule } from './inputs/inputmask/inputmaskdemo.module';
import { ServertdjhipInputSwitchDemoModule } from './inputs/inputswitch/inputswitchdemo.module';
import { ServertdjhipPasswordIndicatorDemoModule } from './inputs/passwordindicator/passwordindicatordemo.module';
import { ServertdjhipAutoCompleteDemoModule } from './inputs/autocomplete/autocompletedemo.module';
import { ServertdjhipSliderDemoModule } from './inputs/slider/sliderdemo.module';
import { ServertdjhipSpinnerDemoModule } from './inputs/spinner/spinnerdemo.module';
import { ServertdjhipRatingDemoModule } from './inputs/rating/ratingdemo.module';
import { ServertdjhipSelectDemoModule } from './inputs/select/selectdemo.module';
import { ServertdjhipSelectButtonDemoModule } from './inputs/selectbutton/selectbuttondemo.module';
import { ServertdjhipListboxDemoModule } from './inputs/listbox/listboxdemo.module';
import { ServertdjhipRadioButtonDemoModule } from './inputs/radiobutton/radiobuttondemo.module';
import { ServertdjhipToggleButtonDemoModule } from './inputs/togglebutton/togglebuttondemo.module';
import { ServertdjhipEditorDemoModule } from './inputs/editor/editordemo.module';

import { ServertdjhipGrowlDemoModule } from './messages/growl/growldemo.module';
import { ServertdjhipMessagesDemoModule } from './messages/messages/messagesdemo.module';
import { ServertdjhipGalleriaDemoModule } from './multimedia/galleria/galleriademo.module';

import { ServertdjhipFileUploadDemoModule } from './fileupload/fileupload/fileuploaddemo.module';

import { ServertdjhipAccordionDemoModule } from './panel/accordion/accordiondemo.module';
import { ServertdjhipPanelDemoModule } from './panel/panel/paneldemo.module';
import { ServertdjhipTabViewDemoModule } from './panel/tabview/tabviewdemo.module';
import { ServertdjhipFieldsetDemoModule } from './panel/fieldset/fieldsetdemo.module';
import { ServertdjhipToolbarDemoModule } from './panel/toolbar/toolbardemo.module';
import { ServertdjhipGridDemoModule } from './panel/grid/griddemo.module';
import { ServertdjhipScrollPanelDemoModule } from './panel/scrollpanel/scrollpaneldemo.module';
import { ServertdjhipCardDemoModule } from './panel/card/carddemo.module';

import { ServertdjhipDataTableDemoModule } from './data/datatable/datatabledemo.module';
import { ServertdjhipTableDemoModule } from './data/table/tabledemo.module';
import { ServertdjhipDataGridDemoModule } from './data/datagrid/datagriddemo.module';
import { ServertdjhipDataListDemoModule } from './data/datalist/datalistdemo.module';
import { ServertdjhipDataScrollerDemoModule } from './data/datascroller/datascrollerdemo.module';
import { ServertdjhipPickListDemoModule } from './data/picklist/picklistdemo.module';
import { ServertdjhipOrderListDemoModule } from './data/orderlist/orderlistdemo.module';
import { ServertdjhipScheduleDemoModule } from './data/schedule/scheduledemo.module';
import { ServertdjhipTreeDemoModule } from './data/tree/treedemo.module';
import { ServertdjhipTreeTableDemoModule } from './data/treetable/treetabledemo.module';
import { ServertdjhipPaginatorDemoModule } from './data/paginator/paginatordemo.module';
import { ServertdjhipGmapDemoModule } from './data/gmap/gmapdemo.module';
import { ServertdjhipOrgChartDemoModule } from './data/orgchart/orgchartdemo.module';
import { ServertdjhipCarouselDemoModule } from './data/carousel/carouseldemo.module';
import { ServertdjhipDataViewDemoModule } from './data/dataview/dataviewdemo.module';

import { ServertdjhipBarchartDemoModule } from './charts/barchart/barchartdemo.module';
import { ServertdjhipDoughnutchartDemoModule } from './charts/doughnutchart/doughnutchartdemo.module';
import { ServertdjhipLinechartDemoModule } from './charts/linechart/linechartdemo.module';
import { ServertdjhipPiechartDemoModule } from './charts/piechart/piechartdemo.module';
import { ServertdjhipPolarareachartDemoModule } from './charts/polarareachart/polarareachartdemo.module';
import { ServertdjhipRadarchartDemoModule } from './charts/radarchart/radarchartdemo.module';

import { ServertdjhipDragDropDemoModule } from './dragdrop/dragdrop/dragdropdemo.module';

import { ServertdjhipMenuDemoModule } from './menu/menu/menudemo.module';
import { ServertdjhipContextMenuDemoModule } from './menu/contextmenu/contextmenudemo.module';
import { ServertdjhipPanelMenuDemoModule } from './menu/panelmenu/panelmenudemo.module';
import { ServertdjhipStepsDemoModule } from './menu/steps/stepsdemo.module';
import { ServertdjhipTieredMenuDemoModule } from './menu/tieredmenu/tieredmenudemo.module';
import { ServertdjhipBreadcrumbDemoModule } from './menu/breadcrumb/breadcrumbdemo.module';
import { ServertdjhipMegaMenuDemoModule } from './menu/megamenu/megamenudemo.module';
import { ServertdjhipMenuBarDemoModule } from './menu/menubar/menubardemo.module';
import { ServertdjhipSlideMenuDemoModule } from './menu/slidemenu/slidemenudemo.module';
import { ServertdjhipTabMenuDemoModule } from './menu/tabmenu/tabmenudemo.module';

import { ServertdjhipBlockUIDemoModule } from './misc/blockui/blockuidemo.module';
import { ServertdjhipCaptchaDemoModule } from './misc/captcha/captchademo.module';
import { ServertdjhipDeferDemoModule } from './misc/defer/deferdemo.module';
import { ServertdjhipInplaceDemoModule } from './misc/inplace/inplacedemo.module';
import { ServertdjhipProgressBarDemoModule } from './misc/progressbar/progressbardemo.module';
import { ServertdjhipRTLDemoModule } from './misc/rtl/rtldemo.module';
import { ServertdjhipTerminalDemoModule } from './misc/terminal/terminaldemo.module';
import { ServertdjhipValidationDemoModule } from './misc/validation/validationdemo.module';
import { ServertdjhipProgressSpinnerDemoModule } from './misc/progressspinner/progressspinnerdemo.module';

@NgModule({
    imports: [

        ServertdjhipMenuDemoModule,
        ServertdjhipContextMenuDemoModule,
        ServertdjhipPanelMenuDemoModule,
        ServertdjhipStepsDemoModule,
        ServertdjhipTieredMenuDemoModule,
        ServertdjhipBreadcrumbDemoModule,
        ServertdjhipMegaMenuDemoModule,
        ServertdjhipMenuBarDemoModule,
        ServertdjhipSlideMenuDemoModule,
        ServertdjhipTabMenuDemoModule,

        ServertdjhipBlockUIDemoModule,
        ServertdjhipCaptchaDemoModule,
        ServertdjhipDeferDemoModule,
        ServertdjhipInplaceDemoModule,
        ServertdjhipProgressBarDemoModule,
        ServertdjhipInputMaskDemoModule,
        ServertdjhipRTLDemoModule,
        ServertdjhipTerminalDemoModule,
        ServertdjhipValidationDemoModule,

        ServertdjhipButtonDemoModule,
        ServertdjhipSplitbuttonDemoModule,

        ServertdjhipInputTextDemoModule,
        ServertdjhipInputTextAreaDemoModule,
        ServertdjhipInputGroupDemoModule,
        ServertdjhipCalendarDemoModule,
        ServertdjhipChipsDemoModule,
        ServertdjhipInputMaskDemoModule,
        ServertdjhipInputSwitchDemoModule,
        ServertdjhipPasswordIndicatorDemoModule,
        ServertdjhipAutoCompleteDemoModule,
        ServertdjhipSliderDemoModule,
        ServertdjhipSpinnerDemoModule,
        ServertdjhipRatingDemoModule,
        ServertdjhipSelectDemoModule,
        ServertdjhipSelectButtonDemoModule,
        ServertdjhipListboxDemoModule,
        ServertdjhipRadioButtonDemoModule,
        ServertdjhipToggleButtonDemoModule,
        ServertdjhipEditorDemoModule,
        ServertdjhipColorPickerDemoModule,
        ServertdjhipCheckboxDemoModule,
        ServertdjhipKeyFilterDemoModule,

        ServertdjhipGrowlDemoModule,
        ServertdjhipMessagesDemoModule,
        ServertdjhipGalleriaDemoModule,

        ServertdjhipFileUploadDemoModule,

        ServertdjhipAccordionDemoModule,
        ServertdjhipPanelDemoModule,
        ServertdjhipTabViewDemoModule,
        ServertdjhipFieldsetDemoModule,
        ServertdjhipToolbarDemoModule,
        ServertdjhipGridDemoModule,
        ServertdjhipScrollPanelDemoModule,
        ServertdjhipCardDemoModule,

        ServertdjhipBarchartDemoModule,
        ServertdjhipDoughnutchartDemoModule,
        ServertdjhipLinechartDemoModule,
        ServertdjhipPiechartDemoModule,
        ServertdjhipPolarareachartDemoModule,
        ServertdjhipRadarchartDemoModule,

        ServertdjhipDragDropDemoModule,

        ServertdjhipDialogDemoModule,
        ServertdjhipConfirmDialogDemoModule,
        ServertdjhipLightboxDemoModule,
        ServertdjhipTooltipDemoModule,
        ServertdjhipOverlayPanelDemoModule,
        ServertdjhipSideBarDemoModule,

        ServertdjhipDataTableDemoModule,
        ServertdjhipTableDemoModule,
        ServertdjhipDataGridDemoModule,
        ServertdjhipDataListDemoModule,
        ServertdjhipDataViewDemoModule,
        ServertdjhipDataScrollerDemoModule,
        ServertdjhipScheduleDemoModule,
        ServertdjhipOrderListDemoModule,
        ServertdjhipPickListDemoModule,
        ServertdjhipTreeDemoModule,
        ServertdjhipTreeTableDemoModule,
        ServertdjhipPaginatorDemoModule,
        ServertdjhipOrgChartDemoModule,
        ServertdjhipGmapDemoModule,
        ServertdjhipCarouselDemoModule,
        ServertdjhipProgressSpinnerDemoModule

    ],
    declarations: [],
    entryComponents: [],
    providers: [],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class ServertdjhipprimengModule {}
