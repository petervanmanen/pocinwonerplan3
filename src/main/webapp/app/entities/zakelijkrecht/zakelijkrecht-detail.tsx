import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './zakelijkrecht.reducer';

export const ZakelijkrechtDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const zakelijkrechtEntity = useAppSelector(state => state.zakelijkrecht.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="zakelijkrechtDetailsHeading">Zakelijkrecht</h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">ID</span>
          </dt>
          <dd>{zakelijkrechtEntity.id}</dd>
          <dt>
            <span id="datumeinde">Datumeinde</span>
          </dt>
          <dd>
            {zakelijkrechtEntity.datumeinde ? (
              <TextFormat value={zakelijkrechtEntity.datumeinde} type="date" format={APP_LOCAL_DATE_FORMAT} />
            ) : null}
          </dd>
          <dt>
            <span id="datumstart">Datumstart</span>
          </dt>
          <dd>
            {zakelijkrechtEntity.datumstart ? (
              <TextFormat value={zakelijkrechtEntity.datumstart} type="date" format={APP_LOCAL_DATE_FORMAT} />
            ) : null}
          </dd>
          <dt>
            <span id="kosten">Kosten</span>
          </dt>
          <dd>{zakelijkrechtEntity.kosten}</dd>
          <dt>
            <span id="soort">Soort</span>
          </dt>
          <dd>{zakelijkrechtEntity.soort}</dd>
        </dl>
        <Button tag={Link} to="/zakelijkrecht" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" /> <span className="d-none d-md-inline">Back</span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/zakelijkrecht/${zakelijkrechtEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
        </Button>
      </Col>
    </Row>
  );
};

export default ZakelijkrechtDetail;
