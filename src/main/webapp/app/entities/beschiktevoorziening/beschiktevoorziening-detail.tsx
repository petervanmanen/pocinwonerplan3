import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './beschiktevoorziening.reducer';

export const BeschiktevoorzieningDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const beschiktevoorzieningEntity = useAppSelector(state => state.beschiktevoorziening.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="beschiktevoorzieningDetailsHeading">Beschiktevoorziening</h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">ID</span>
          </dt>
          <dd>{beschiktevoorzieningEntity.id}</dd>
          <dt>
            <span id="code">Code</span>
          </dt>
          <dd>{beschiktevoorzieningEntity.code}</dd>
          <dt>
            <span id="datumeinde">Datumeinde</span>
          </dt>
          <dd>
            {beschiktevoorzieningEntity.datumeinde ? (
              <TextFormat value={beschiktevoorzieningEntity.datumeinde} type="date" format={APP_LOCAL_DATE_FORMAT} />
            ) : null}
          </dd>
          <dt>
            <span id="datumeindeoorspronkelijk">Datumeindeoorspronkelijk</span>
          </dt>
          <dd>
            {beschiktevoorzieningEntity.datumeindeoorspronkelijk ? (
              <TextFormat value={beschiktevoorzieningEntity.datumeindeoorspronkelijk} type="date" format={APP_LOCAL_DATE_FORMAT} />
            ) : null}
          </dd>
          <dt>
            <span id="datumstart">Datumstart</span>
          </dt>
          <dd>
            {beschiktevoorzieningEntity.datumstart ? (
              <TextFormat value={beschiktevoorzieningEntity.datumstart} type="date" format={APP_LOCAL_DATE_FORMAT} />
            ) : null}
          </dd>
          <dt>
            <span id="eenheid">Eenheid</span>
          </dt>
          <dd>{beschiktevoorzieningEntity.eenheid}</dd>
          <dt>
            <span id="frequentie">Frequentie</span>
          </dt>
          <dd>{beschiktevoorzieningEntity.frequentie}</dd>
          <dt>
            <span id="leveringsvorm">Leveringsvorm</span>
          </dt>
          <dd>{beschiktevoorzieningEntity.leveringsvorm}</dd>
          <dt>
            <span id="omvang">Omvang</span>
          </dt>
          <dd>{beschiktevoorzieningEntity.omvang}</dd>
          <dt>
            <span id="redeneinde">Redeneinde</span>
          </dt>
          <dd>{beschiktevoorzieningEntity.redeneinde}</dd>
          <dt>
            <span id="status">Status</span>
          </dt>
          <dd>{beschiktevoorzieningEntity.status}</dd>
          <dt>
            <span id="wet">Wet</span>
          </dt>
          <dd>{beschiktevoorzieningEntity.wet}</dd>
        </dl>
        <Button tag={Link} to="/beschiktevoorziening" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" /> <span className="d-none d-md-inline">Back</span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/beschiktevoorziening/${beschiktevoorzieningEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
        </Button>
      </Col>
    </Row>
  );
};

export default BeschiktevoorzieningDetail;
