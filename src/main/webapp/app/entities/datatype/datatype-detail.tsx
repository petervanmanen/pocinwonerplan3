import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './datatype.reducer';

export const DatatypeDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const datatypeEntity = useAppSelector(state => state.datatype.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="datatypeDetailsHeading">Datatype</h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="datumopname">Datumopname</span>
          </dt>
          <dd>
            {datatypeEntity.datumopname ? (
              <TextFormat value={datatypeEntity.datumopname} type="date" format={APP_LOCAL_DATE_FORMAT} />
            ) : null}
          </dd>
          <dt>
            <span id="definitie">Definitie</span>
          </dt>
          <dd>{datatypeEntity.definitie}</dd>
          <dt>
            <span id="domein">Domein</span>
          </dt>
          <dd>{datatypeEntity.domein}</dd>
          <dt>
            <span id="eaguid">Eaguid</span>
          </dt>
          <dd>{datatypeEntity.eaguid}</dd>
          <dt>
            <span id="herkomst">Herkomst</span>
          </dt>
          <dd>{datatypeEntity.herkomst}</dd>
          <dt>
            <span id="id">Id</span>
          </dt>
          <dd>{datatypeEntity.id}</dd>
          <dt>
            <span id="kardinaliteit">Kardinaliteit</span>
          </dt>
          <dd>{datatypeEntity.kardinaliteit}</dd>
          <dt>
            <span id="lengte">Lengte</span>
          </dt>
          <dd>{datatypeEntity.lengte}</dd>
          <dt>
            <span id="naam">Naam</span>
          </dt>
          <dd>{datatypeEntity.naam}</dd>
          <dt>
            <span id="patroon">Patroon</span>
          </dt>
          <dd>{datatypeEntity.patroon}</dd>
          <dt>
            <span id="toelichting">Toelichting</span>
          </dt>
          <dd>{datatypeEntity.toelichting}</dd>
        </dl>
        <Button tag={Link} to="/datatype" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" /> <span className="d-none d-md-inline">Back</span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/datatype/${datatypeEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
        </Button>
      </Col>
    </Row>
  );
};

export default DatatypeDetail;
