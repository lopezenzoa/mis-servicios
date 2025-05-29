import { ComponentFixture, TestBed } from '@angular/core/testing';

import { PrestadoresListadoComponent } from './prestadores-listado.component';

describe('PrestadoresListadoComponent', () => {
  let component: PrestadoresListadoComponent;
  let fixture: ComponentFixture<PrestadoresListadoComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [PrestadoresListadoComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(PrestadoresListadoComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
