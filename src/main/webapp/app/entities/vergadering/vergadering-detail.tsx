import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import {} from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './vergadering.reducer';

export const VergaderingDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const vergaderingEntity = useAppSelector(state => state.vergadering.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="vergaderingDetailsHeading">Vergadering</h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">ID</span>
          </dt>
          <dd>{vergaderingEntity.id}</dd>
          <dt>
            <span id="eindtijd">Eindtijd</span>
          </dt>
          <dd>{vergaderingEntity.eindtijd}</dd>
          <dt>
            <span id="locatie">Locatie</span>
          </dt>
          <dd>{vergaderingEntity.locatie}</dd>
          <dt>
            <span id="starttijd">Starttijd</span>
          </dt>
          <dd>{vergaderingEntity.starttijd}</dd>
          <dt>
            <span id="titel">Titel</span>
          </dt>
          <dd>{vergaderingEntity.titel}</dd>
          <dt>Heeftverslag Raadsstuk</dt>
          <dd>{vergaderingEntity.heeftverslagRaadsstuk ? vergaderingEntity.heeftverslagRaadsstuk.id : ''}</dd>
          <dt>Heeft Raadscommissie</dt>
          <dd>{vergaderingEntity.heeftRaadscommissie ? vergaderingEntity.heeftRaadscommissie.id : ''}</dd>
          <dt>Wordtbehandeldin Raadsstuk</dt>
          <dd>
            {vergaderingEntity.wordtbehandeldinRaadsstuks
              ? vergaderingEntity.wordtbehandeldinRaadsstuks.map((val, i) => (
                  <span key={val.id}>
                    <a>{val.id}</a>
                    {vergaderingEntity.wordtbehandeldinRaadsstuks && i === vergaderingEntity.wordtbehandeldinRaadsstuks.length - 1
                      ? ''
                      : ', '}
                  </span>
                ))
              : null}
          </dd>
        </dl>
        <Button tag={Link} to="/vergadering" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" /> <span className="d-none d-md-inline">Back</span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/vergadering/${vergaderingEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
        </Button>
      </Col>
    </Row>
  );
};

export default VergaderingDetail;
