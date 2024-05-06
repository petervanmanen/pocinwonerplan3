import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './vacature.reducer';

export const VacatureDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const vacatureEntity = useAppSelector(state => state.vacature.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="vacatureDetailsHeading">Vacature</h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">ID</span>
          </dt>
          <dd>{vacatureEntity.id}</dd>
          <dt>
            <span id="datumgesloten">Datumgesloten</span>
          </dt>
          <dd>
            {vacatureEntity.datumgesloten ? (
              <TextFormat value={vacatureEntity.datumgesloten} type="date" format={APP_LOCAL_DATE_FORMAT} />
            ) : null}
          </dd>
          <dt>
            <span id="datumopengesteld">Datumopengesteld</span>
          </dt>
          <dd>{vacatureEntity.datumopengesteld}</dd>
          <dt>
            <span id="deeltijd">Deeltijd</span>
          </dt>
          <dd>{vacatureEntity.deeltijd ? 'true' : 'false'}</dd>
          <dt>
            <span id="extern">Extern</span>
          </dt>
          <dd>{vacatureEntity.extern ? 'true' : 'false'}</dd>
          <dt>
            <span id="intern">Intern</span>
          </dt>
          <dd>{vacatureEntity.intern ? 'true' : 'false'}</dd>
          <dt>
            <span id="vastedienst">Vastedienst</span>
          </dt>
          <dd>{vacatureEntity.vastedienst ? 'true' : 'false'}</dd>
          <dt>Vacaturebijfunctie Functie</dt>
          <dd>{vacatureEntity.vacaturebijfunctieFunctie ? vacatureEntity.vacaturebijfunctieFunctie.id : ''}</dd>
        </dl>
        <Button tag={Link} to="/vacature" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" /> <span className="d-none d-md-inline">Back</span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/vacature/${vacatureEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
        </Button>
      </Col>
    </Row>
  );
};

export default VacatureDetail;
