import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './levering.reducer';

export const LeveringDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const leveringEntity = useAppSelector(state => state.levering.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="leveringDetailsHeading">Levering</h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">ID</span>
          </dt>
          <dd>{leveringEntity.id}</dd>
          <dt>
            <span id="code">Code</span>
          </dt>
          <dd>{leveringEntity.code}</dd>
          <dt>
            <span id="datumstart">Datumstart</span>
          </dt>
          <dd>
            {leveringEntity.datumstart ? <TextFormat value={leveringEntity.datumstart} type="date" format={APP_LOCAL_DATE_FORMAT} /> : null}
          </dd>
          <dt>
            <span id="datumstop">Datumstop</span>
          </dt>
          <dd>
            {leveringEntity.datumstop ? <TextFormat value={leveringEntity.datumstop} type="date" format={APP_LOCAL_DATE_FORMAT} /> : null}
          </dd>
          <dt>
            <span id="eenheid">Eenheid</span>
          </dt>
          <dd>{leveringEntity.eenheid}</dd>
          <dt>
            <span id="frequentie">Frequentie</span>
          </dt>
          <dd>{leveringEntity.frequentie}</dd>
          <dt>
            <span id="omvang">Omvang</span>
          </dt>
          <dd>{leveringEntity.omvang}</dd>
          <dt>
            <span id="stopreden">Stopreden</span>
          </dt>
          <dd>{leveringEntity.stopreden}</dd>
          <dt>Geleverdeprestatie Beschikking</dt>
          <dd>{leveringEntity.geleverdeprestatieBeschikking ? leveringEntity.geleverdeprestatieBeschikking.id : ''}</dd>
          <dt>Prestatievoor Client</dt>
          <dd>{leveringEntity.prestatievoorClient ? leveringEntity.prestatievoorClient.id : ''}</dd>
          <dt>Geleverdezorg Toewijzing</dt>
          <dd>{leveringEntity.geleverdezorgToewijzing ? leveringEntity.geleverdezorgToewijzing.id : ''}</dd>
          <dt>Voorziening Voorziening</dt>
          <dd>{leveringEntity.voorzieningVoorziening ? leveringEntity.voorzieningVoorziening.id : ''}</dd>
          <dt>Leverdeprestatie Leverancier</dt>
          <dd>{leveringEntity.leverdeprestatieLeverancier ? leveringEntity.leverdeprestatieLeverancier.id : ''}</dd>
        </dl>
        <Button tag={Link} to="/levering" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" /> <span className="d-none d-md-inline">Back</span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/levering/${leveringEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
        </Button>
      </Col>
    </Row>
  );
};

export default LeveringDetail;
