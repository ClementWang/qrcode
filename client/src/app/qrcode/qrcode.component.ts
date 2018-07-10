import { Component, OnInit } from '@angular/core';

@Component({
  selector: 'app-qrcode',
  templateUrl: './qrcode.component.html',
  styleUrls: ['./qrcode.component.css']
})
export class QrcodeComponent implements OnInit {
  scannerEnabled = true;
  autofocusEnabled = true;

  constructor() { }

  ngOnInit() {
  }

  displayCameras(event) {
    console.log('AAAAAAAAAAAA');
    console.log(event);
  }

  camerasNotFound() {
    console.error('Cameras not found!!!');
  }

  handleQrCodeResult(event) {
    console.log('BBBBBBBBBB');
    console.log(event);
  }

}
