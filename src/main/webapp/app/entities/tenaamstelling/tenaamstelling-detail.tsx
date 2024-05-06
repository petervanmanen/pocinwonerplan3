import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './tenaamstelling.reducer';

export const TenaamstellingDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const tenaamstellingEntity = useAppSelector(state => state.tenaamstelling.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="tenaamstellingDetailsHeading">Tenaamstelling</h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">ID</span>
          </dt>
          <dd>{tenaamstellingEntity.id}</dd>
          <dt>
            <span id="aandeelinrecht">Aandeelinrecht</span>
          </dt>
          <dd>{tenaamstellingEntity.aandeelinrecht}</dd>
          <dt>
            <span id="burgerlijkestaattentijdevanverkrijging">Burgerlijkestaattentijdevanverkrijging</span>
          </dt>
          <dd>{tenaamstellingEntity.burgerlijkestaattentijdevanverkrijging}</dd>
          <dt>
            <span id="datumbegingeldigheid">Datumbegingeldigheid</span>
          </dt>
          <dd>
            {tenaamstellingEntity.datumbegingeldigheid ? (
              <TextFormat value={tenaamstellingEntity.datumbegingeldigheid} type="date" format={APP_LOCAL_DATE_FORMAT} />
            ) : null}
          </dd>
          <dt>
            <span id="datumeindegeldigheid">Datumeindegeldigheid</span>
          </dt>
          <dd>
            {tenaamstellingEntity.datumeindegeldigheid ? (
              <TextFormat value={tenaamstellingEntity.datumeindegeldigheid} type="date" format={APP_LOCAL_DATE_FORMAT} />
            ) : null}
          </dd>
          <dt>
            <span id="exploitantcode">Exploitantcode</span>
          </dt>
          <dd>{tenaamstellingEntity.exploitantcode}</dd>
          <dt>
            <span id="identificatietenaamstelling">Identificatietenaamstelling</span>
          </dt>
          <dd>{tenaamstellingEntity.identificatietenaamstelling}</dd>
          <dt>
            <span id="verklaringinzakederdenbescherming">Verklaringinzakederdenbescherming</span>
          </dt>
          <dd>{tenaamstellingEntity.verklaringinzakederdenbescherming}</dd>
          <dt>
            <span id="verkregennamenssamenwerkingsverband">Verkregennamenssamenwerkingsverband</span>
          </dt>
          <dd>{tenaamstellingEntity.verkregennamenssamenwerkingsverband}</dd>
          <dt>Heeft Rechtspersoon</dt>
          <dd>{tenaamstellingEntity.heeftRechtspersoon ? tenaamstellingEntity.heeftRechtspersoon.id : ''}</dd>
        </dl>
        <Button tag={Link} to="/tenaamstelling" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" /> <span className="d-none d-md-inline">Back</span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/tenaamstelling/${tenaamstellingEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
        </Button>
      </Col>
    </Row>
  );
};

export default TenaamstellingDetail;
