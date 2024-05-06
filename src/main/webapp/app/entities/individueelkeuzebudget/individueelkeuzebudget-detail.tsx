import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './individueelkeuzebudget.reducer';

export const IndividueelkeuzebudgetDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const individueelkeuzebudgetEntity = useAppSelector(state => state.individueelkeuzebudget.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="individueelkeuzebudgetDetailsHeading">Individueelkeuzebudget</h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">ID</span>
          </dt>
          <dd>{individueelkeuzebudgetEntity.id}</dd>
          <dt>
            <span id="bedrag">Bedrag</span>
          </dt>
          <dd>{individueelkeuzebudgetEntity.bedrag}</dd>
          <dt>
            <span id="datumeinde">Datumeinde</span>
          </dt>
          <dd>
            {individueelkeuzebudgetEntity.datumeinde ? (
              <TextFormat value={individueelkeuzebudgetEntity.datumeinde} type="date" format={APP_LOCAL_DATE_FORMAT} />
            ) : null}
          </dd>
          <dt>
            <span id="datumstart">Datumstart</span>
          </dt>
          <dd>
            {individueelkeuzebudgetEntity.datumstart ? (
              <TextFormat value={individueelkeuzebudgetEntity.datumstart} type="date" format={APP_LOCAL_DATE_FORMAT} />
            ) : null}
          </dd>
          <dt>
            <span id="datumtoekenning">Datumtoekenning</span>
          </dt>
          <dd>
            {individueelkeuzebudgetEntity.datumtoekenning ? (
              <TextFormat value={individueelkeuzebudgetEntity.datumtoekenning} type="date" format={APP_LOCAL_DATE_FORMAT} />
            ) : null}
          </dd>
        </dl>
        <Button tag={Link} to="/individueelkeuzebudget" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" /> <span className="d-none d-md-inline">Back</span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/individueelkeuzebudget/${individueelkeuzebudgetEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
        </Button>
      </Col>
    </Row>
  );
};

export default IndividueelkeuzebudgetDetail;
