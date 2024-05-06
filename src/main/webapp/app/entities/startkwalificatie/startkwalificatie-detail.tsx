import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './startkwalificatie.reducer';

export const StartkwalificatieDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const startkwalificatieEntity = useAppSelector(state => state.startkwalificatie.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="startkwalificatieDetailsHeading">Startkwalificatie</h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">ID</span>
          </dt>
          <dd>{startkwalificatieEntity.id}</dd>
          <dt>
            <span id="datumbehaald">Datumbehaald</span>
          </dt>
          <dd>
            {startkwalificatieEntity.datumbehaald ? (
              <TextFormat value={startkwalificatieEntity.datumbehaald} type="date" format={APP_LOCAL_DATE_FORMAT} />
            ) : null}
          </dd>
        </dl>
        <Button tag={Link} to="/startkwalificatie" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" /> <span className="d-none d-md-inline">Back</span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/startkwalificatie/${startkwalificatieEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
        </Button>
      </Col>
    </Row>
  );
};

export default StartkwalificatieDetail;
