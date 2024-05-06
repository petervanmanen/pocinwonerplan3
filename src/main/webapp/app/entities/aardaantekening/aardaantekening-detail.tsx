import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './aardaantekening.reducer';

export const AardaantekeningDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const aardaantekeningEntity = useAppSelector(state => state.aardaantekening.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="aardaantekeningDetailsHeading">Aardaantekening</h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">ID</span>
          </dt>
          <dd>{aardaantekeningEntity.id}</dd>
          <dt>
            <span id="codeaardaantekening">Codeaardaantekening</span>
          </dt>
          <dd>{aardaantekeningEntity.codeaardaantekening}</dd>
          <dt>
            <span id="datumbegingeldigheidaardaantekening">Datumbegingeldigheidaardaantekening</span>
          </dt>
          <dd>
            {aardaantekeningEntity.datumbegingeldigheidaardaantekening ? (
              <TextFormat value={aardaantekeningEntity.datumbegingeldigheidaardaantekening} type="date" format={APP_LOCAL_DATE_FORMAT} />
            ) : null}
          </dd>
          <dt>
            <span id="datumeindegeldigheidaardaantekening">Datumeindegeldigheidaardaantekening</span>
          </dt>
          <dd>
            {aardaantekeningEntity.datumeindegeldigheidaardaantekening ? (
              <TextFormat value={aardaantekeningEntity.datumeindegeldigheidaardaantekening} type="date" format={APP_LOCAL_DATE_FORMAT} />
            ) : null}
          </dd>
          <dt>
            <span id="naamaardaantekening">Naamaardaantekening</span>
          </dt>
          <dd>{aardaantekeningEntity.naamaardaantekening}</dd>
        </dl>
        <Button tag={Link} to="/aardaantekening" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" /> <span className="d-none d-md-inline">Back</span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/aardaantekening/${aardaantekeningEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
        </Button>
      </Col>
    </Row>
  );
};

export default AardaantekeningDetail;
