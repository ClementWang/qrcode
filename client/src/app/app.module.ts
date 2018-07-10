import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { ZXingScannerModule } from '@zxing/ngx-scanner';
import { NgxKjuaModule } from 'ngx-kjua';

import { AppComponent } from './app.component';
import { QrcodeComponent } from './qrcode/qrcode.component';


@NgModule({
  declarations: [
    AppComponent,
    QrcodeComponent
  ],
  imports: [
    BrowserModule,
    FormsModule,
    ZXingScannerModule.forRoot(),
    NgxKjuaModule
  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule { }
