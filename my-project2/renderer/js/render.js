import * as THREE from 'three';
import { OrbitControls } from 'three/addons/controls/OrbitControls.js';
import { GLTFLoader } from 'three/addons/loaders/GLTFLoader.js'

//초기 설정 렌더러 사이즈 포지션
const renderer = new THREE.WebGLRenderer();

renderer.setSize( window.innerWidth, window.innerHeight )
document.body.appendChild( renderer.domElement );

//카메라 위치 조절
const camera = new THREE.PerspectiveCamera( 45, window.innerWidth /
    window.innerHeight, 0.1, 300 );// FOV 화각 조절 확대 8~28 표준 47 원근 63~84 aspect 화면 비율, 랜더링 거리 최소 ~ 최대
camera.position.set( 0, 0, 75 );
camera.lookAt( 0, 0, 0 );

const scene = new THREE.Scene();
scene.background = new THREE.Color('white');

//드레그 카메라 이동 모듈
var controls = new OrbitControls( camera, renderer.domElement );
controls.rotateSpeed = 1.0; // 마우스로 카메라를 회전시킬 속도입니다. 기본값(Float)은 1입니다.
controls.zoomSpeed = 1.2; // 마우스 휠로 카메라를 줌 시키는 속도 입니다. 기본값(Float)은 1입니다.
controls.panSpeed = 0.8; // 패닝 속도 입니다. 기본값(Float)은 1입니다.
controls.minDistance = 25; // 마우스 휠로 카메라 거리 조작시 최소 값. 기본값(Float)은 0 입니다.
controls.maxDistance = 200; // 마우스 휠로 카메라 거리 조작시 최대 값. 기본값(Float)은 무제한 입니다.

renderer.gammaFactor = 1;


init()
animate()

function init(){

    var loader = new GLTFLoader();
    let model = new THREE.Object3D();

    const ambientLight = new THREE.AmbientLight(0xffffff, 1);
    scene.add(ambientLight);

    const pointLight = new THREE.PointLight(0xffffff, 1);
    pointLight.position.set(100, 100, 100);
    scene.add(pointLight);


    loader.load(
        // resource URL

        '../obj/2.glb',
        // called when resource is loaded
        function ( gltf ) {

            model = gltf.scene;
            model.position.set(0, -20, 0);
            model.scale.set(0.02, 0.02, 0.02);

            // 모델에 적용된 마테리얼에 색상값 설정
            model.traverse((child) => {
                if (child.isMesh) {
                    child.material.color.set(0xffffff);
                }
            });

            scene.add(model);
        },
        // called when loading is in progresses
        function ( xhr ) {

            console.log( ( xhr.loaded / xhr.total * 100 ) + '% loaded' );

        },
        // called when loading has errors
        function ( error ) {

            console.log( 'An error happened' );

        }
    );

    //드레그 카메라 이동 모듈




}


function animate() {
    requestAnimationFrame( animate );
    renderer.render( scene, camera );
    controls.update();
}