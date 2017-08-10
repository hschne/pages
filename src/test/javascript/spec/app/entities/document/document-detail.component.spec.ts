/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async, inject } from '@angular/core/testing';
import { OnInit } from '@angular/core';
import { DatePipe } from '@angular/common';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs/Rx';
import { JhiDateUtils, JhiDataUtils, JhiEventManager } from 'ng-jhipster';
import { PagesTestModule } from '../../../test.module';
import { MockActivatedRoute } from '../../../helpers/mock-route.service';
import {PageService} from '../../../../../../main/webapp/app/pages/page.service';
import {PageViewComponent} from '../../../../../../main/webapp/app/pages/page-view.component';
import {Page} from '../../../../../../main/webapp/app/pages/page.model';

describe('Component Tests', () => {

    describe('Page Management Detail Component', () => {
        let comp: PageViewComponent;
        let fixture: ComponentFixture<PageViewComponent>;
        let service: PageService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [PagesTestModule],
                declarations: [PageViewComponent],
                providers: [
                    JhiDateUtils,
                    JhiDataUtils,
                    DatePipe,
                    {
                        provide: ActivatedRoute,
                        useValue: new MockActivatedRoute({id: 123})
                    },
                    PageService,
                    JhiEventManager
                ]
            }).overrideTemplate(PageViewComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(PageViewComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(PageService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
            // GIVEN

            spyOn(service, 'find').and.returnValue(Observable.of(new Page(10)));

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.find).toHaveBeenCalledWith(123);
            expect(comp.page).toEqual(jasmine.objectContaining({id: 10}));
            });
        });
    });

});
