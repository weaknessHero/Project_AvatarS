import { ComponentFixture, TestBed } from '@angular/core/testing';

import { MyavatarComponent } from './myavatar.component';

describe('MyavatarComponent', () => {
  let component: MyavatarComponent;
  let fixture: ComponentFixture<MyavatarComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ MyavatarComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(MyavatarComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
