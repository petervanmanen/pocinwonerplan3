import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './locatieaanduidingwozobject.reducer';

export const LocatieaanduidingwozobjectDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const locatieaanduidingwozobjectEntity = useAppSelector(state => state.locatieaanduidingwozobject.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="locatieaanduidingwozobjectDetailsHeading">Locatieaanduidingwozobject</h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">ID</span>
          </dt>
          <dd>{locatieaanduidingwozobjectEntity.id}</dd>
          <dt>
            <span id="datumbegingeldigheid">Datumbegingeldigheid</span>
          </dt>
          <dd>
            {locatieaanduidingwozobjectEntity.datumbegingeldigheid ? (
              <TextFormat value={locatieaanduidingwozobjectEntity.datumbegingeldigheid} type="date" format={APP_LOCAL_DATE_FORMAT} />
            ) : null}
          </dd>
          <dt>
            <span id="datumeindegeldigheid">Datumeindegeldigheid</span>
          </dt>
          <dd>
            {locatieaanduidingwozobjectEntity.datumeindegeldigheid ? (
              <TextFormat value={locatieaanduidingwozobjectEntity.datumeindegeldigheid} type="date" format={APP_LOCAL_DATE_FORMAT} />
            ) : null}
          </dd>
          <dt>
            <span id="locatieomschrijving">Locatieomschrijving</span>
          </dt>
          <dd>{locatieaanduidingwozobjectEntity.locatieomschrijving}</dd>
          <dt>
            <span id="primair">Primair</span>
          </dt>
          <dd>{locatieaanduidingwozobjectEntity.primair}</dd>
        </dl>
        <Button tag={Link} to="/locatieaanduidingwozobject" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" /> <span className="d-none d-md-inline">Back</span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/locatieaanduidingwozobject/${locatieaanduidingwozobjectEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
        </Button>
      </Col>
    </Row>
  );
};

export default LocatieaanduidingwozobjectDetail;
