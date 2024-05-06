import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './formulierinhuur.reducer';

export const FormulierinhuurDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const formulierinhuurEntity = useAppSelector(state => state.formulierinhuur.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="formulierinhuurDetailsHeading">Formulierinhuur</h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">ID</span>
          </dt>
          <dd>{formulierinhuurEntity.id}</dd>
          <dt>
            <span id="akkoordfinancieeladviseur">Akkoordfinancieeladviseur</span>
          </dt>
          <dd>{formulierinhuurEntity.akkoordfinancieeladviseur}</dd>
          <dt>
            <span id="akkoordhradviseur">Akkoordhradviseur</span>
          </dt>
          <dd>{formulierinhuurEntity.akkoordhradviseur}</dd>
          <dt>
            <span id="datuminganginhuur">Datuminganginhuur</span>
          </dt>
          <dd>
            {formulierinhuurEntity.datuminganginhuur ? (
              <TextFormat value={formulierinhuurEntity.datuminganginhuur} type="date" format={APP_LOCAL_DATE_FORMAT} />
            ) : null}
          </dd>
          <dt>
            <span id="functienaaminhuur">Functienaaminhuur</span>
          </dt>
          <dd>{formulierinhuurEntity.functienaaminhuur}</dd>
        </dl>
        <Button tag={Link} to="/formulierinhuur" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" /> <span className="d-none d-md-inline">Back</span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/formulierinhuur/${formulierinhuurEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
        </Button>
      </Col>
    </Row>
  );
};

export default FormulierinhuurDetail;
