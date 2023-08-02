import { ComponentFixture, TestBed } from '@angular/core/testing';

import { DetectLabelsComponent } from './detect-labels.component';

describe('DetectLabelsComponentComponent', () => {
  let component: DetectLabelsComponent;
  let fixture: ComponentFixture<DetectLabelsComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ DetectLabelsComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(DetectLabelsComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
