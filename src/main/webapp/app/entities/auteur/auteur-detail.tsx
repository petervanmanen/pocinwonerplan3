import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './auteur.reducer';

export const AuteurDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const auteurEntity = useAppSelector(state => state.auteur.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="auteurDetailsHeading">Auteur</h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">ID</span>
          </dt>
          <dd>{auteurEntity.id}</dd>
          <dt>
            <span id="datumgeboorte">Datumgeboorte</span>
          </dt>
          <dd>
            {auteurEntity.datumgeboorte ? (
              <TextFormat value={auteurEntity.datumgeboorte} type="date" format={APP_LOCAL_DATE_FORMAT} />
            ) : null}
          </dd>
          <dt>
            <span id="datumoverlijden">Datumoverlijden</span>
          </dt>
          <dd>
            {auteurEntity.datumoverlijden ? (
              <TextFormat value={auteurEntity.datumoverlijden} type="date" format={APP_LOCAL_DATE_FORMAT} />
            ) : null}
          </dd>
        </dl>
        <Button tag={Link} to="/auteur" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" /> <span className="d-none d-md-inline">Back</span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/auteur/${auteurEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
        </Button>
      </Col>
    </Row>
  );
};

export default AuteurDetail;
