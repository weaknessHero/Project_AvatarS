import { ComponentFixture, TestBed } from '@angular/core/testing';

import { DetectLabelsComponentComponent } from './detect-labels.component';

describe('DetectLabelsComponentComponent', () => {
  let component: DetectLabelsComponentComponent;
  let fixture: ComponentFixture<DetectLabelsComponentComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ DetectLabelsComponentComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(DetectLabelsComponentComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
