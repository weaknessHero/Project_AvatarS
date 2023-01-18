import * as THREE from './three.module.js';
import { OrbitControls } from "https://threejs.org/examples/jsm/controls/OrbitControls.js";

//초기 설정 렌더러 사이즈 포지션
const renderer = new THREE.WebGLRenderer();
renderer.setSize( window.innerWidth, window.innerHeight )
document.body.appendChild( renderer.domElement );

//카메라 위치 조절
const camera = new THREE.PerspectiveCamera( 45, window.innerWidth /
window.innerHeight, 0.1, 500 );// FOV 화각 조절 확대 8~28 표준 47 원근 63~84 aspect 화면 비율, 랜더링 거리 최소 ~ 최대

camera.position.set( 0, 0, 100 );
camera.lookAt( 0, 0, 0 );

const scene = new THREE.Scene();
var line;
var controls;

renderer.domElement.addEventListener( 'mousedown', onDocumentMouseDown, false );
renderer.domElement.addEventListener( 'mousemove', onDocumentMouseMove, false );

init()
animate()

function init(){

  
  const material = new THREE.LineBasicMaterial( { color: 0x0000ff } );

  const points = [];
  points.push( new THREE.Vector3( - 10, 0, 0 ) );
  points.push( new THREE.Vector3( 0, 10, 0 ) );
  points.push( new THREE.Vector3( 10, 0, 0 ) );
  
  const geometry = new THREE.BufferGeometry().setFromPoints( points );

  line = new THREE.Line( geometry, material );
  
  scene.add( line );

  //드레그 카메라 이동 모듈
  controls = new OrbitControls( camera, renderer.domElement );
  controls.rotateSpeed = 1.0; // 마우스로 카메라를 회전시킬 속도입니다. 기본값(Float)은 1입니다.
  controls.zoomSpeed = 1.2; // 마우스 휠로 카메라를 줌 시키는 속도 입니다. 기본값(Float)은 1입니다.
  controls.panSpeed = 0.8; // 패닝 속도 입니다. 기본값(Float)은 1입니다.
  controls.minDistance = 100; // 마우스 휠로 카메라 거리 조작시 최소 값. 기본값(Float)은 0 입니다.
  controls.maxDistance = 100; // 마우스 휠로 카메라 거리 조작시 최대 값. 기본값(Float)은 무제한 입니다.

}


function animate() {
	requestAnimationFrame( animate );
	renderer.render( scene, camera );
  controls.update();
}


// 마우스 이벤트 설정
function onDocumentMouseMove( event ) {

}
      
function onDocumentMouseDown( event ) {

}


