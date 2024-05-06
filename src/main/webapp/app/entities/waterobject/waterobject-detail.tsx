import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import {} from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './waterobject.reducer';

export const WaterobjectDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const waterobjectEntity = useAppSelector(state => state.waterobject.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="waterobjectDetailsHeading">Waterobject</h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">ID</span>
          </dt>
          <dd>{waterobjectEntity.id}</dd>
          <dt>
            <span id="breedte">Breedte</span>
          </dt>
          <dd>{waterobjectEntity.breedte}</dd>
          <dt>
            <span id="folie">Folie</span>
          </dt>
          <dd>{waterobjectEntity.folie ? 'true' : 'false'}</dd>
          <dt>
            <span id="hoogte">Hoogte</span>
          </dt>
          <dd>{waterobjectEntity.hoogte}</dd>
          <dt>
            <span id="infiltrerendoppervlak">Infiltrerendoppervlak</span>
          </dt>
          <dd>{waterobjectEntity.infiltrerendoppervlak}</dd>
          <dt>
            <span id="infiltrerendvermogen">Infiltrerendvermogen</span>
          </dt>
          <dd>{waterobjectEntity.infiltrerendvermogen}</dd>
          <dt>
            <span id="lengte">Lengte</span>
          </dt>
          <dd>{waterobjectEntity.lengte}</dd>
          <dt>
            <span id="lozingspunt">Lozingspunt</span>
          </dt>
          <dd>{waterobjectEntity.lozingspunt}</dd>
          <dt>
            <span id="oppervlakte">Oppervlakte</span>
          </dt>
          <dd>{waterobjectEntity.oppervlakte}</dd>
          <dt>
            <span id="porositeit">Porositeit</span>
          </dt>
          <dd>{waterobjectEntity.porositeit}</dd>
          <dt>
            <span id="streefdiepte">Streefdiepte</span>
          </dt>
          <dd>{waterobjectEntity.streefdiepte}</dd>
          <dt>
            <span id="type">Type</span>
          </dt>
          <dd>{waterobjectEntity.type}</dd>
          <dt>
            <span id="typeplus">Typeplus</span>
          </dt>
          <dd>{waterobjectEntity.typeplus}</dd>
          <dt>
            <span id="typeplus2">Typeplus 2</span>
          </dt>
          <dd>{waterobjectEntity.typeplus2}</dd>
          <dt>
            <span id="typevaarwater">Typevaarwater</span>
          </dt>
          <dd>{waterobjectEntity.typevaarwater}</dd>
          <dt>
            <span id="typewaterplant">Typewaterplant</span>
          </dt>
          <dd>{waterobjectEntity.typewaterplant}</dd>
          <dt>
            <span id="uitstroomniveau">Uitstroomniveau</span>
          </dt>
          <dd>{waterobjectEntity.uitstroomniveau}</dd>
          <dt>
            <span id="vaarwegtraject">Vaarwegtraject</span>
          </dt>
          <dd>{waterobjectEntity.vaarwegtraject}</dd>
          <dt>
            <span id="vorm">Vorm</span>
          </dt>
          <dd>{waterobjectEntity.vorm}</dd>
          <dt>
            <span id="waternaam">Waternaam</span>
          </dt>
          <dd>{waterobjectEntity.waternaam}</dd>
          <dt>
            <span id="waterpeil">Waterpeil</span>
          </dt>
          <dd>{waterobjectEntity.waterpeil}</dd>
          <dt>
            <span id="waterpeilwinter">Waterpeilwinter</span>
          </dt>
          <dd>{waterobjectEntity.waterpeilwinter}</dd>
          <dt>
            <span id="waterpeilzomer">Waterpeilzomer</span>
          </dt>
          <dd>{waterobjectEntity.waterpeilzomer}</dd>
          <dt>
            <span id="waterplanten">Waterplanten</span>
          </dt>
          <dd>{waterobjectEntity.waterplanten ? 'true' : 'false'}</dd>
        </dl>
        <Button tag={Link} to="/waterobject" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" /> <span className="d-none d-md-inline">Back</span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/waterobject/${waterobjectEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
        </Button>
      </Col>
    </Row>
  );
};

export default WaterobjectDetail;
