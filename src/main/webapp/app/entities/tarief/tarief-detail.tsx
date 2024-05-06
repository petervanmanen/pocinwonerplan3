import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './tarief.reducer';

export const TariefDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const tariefEntity = useAppSelector(state => state.tarief.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="tariefDetailsHeading">Tarief</h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">ID</span>
          </dt>
          <dd>{tariefEntity.id}</dd>
          <dt>
            <span id="bedrag">Bedrag</span>
          </dt>
          <dd>{tariefEntity.bedrag}</dd>
          <dt>
            <span id="datumeinde">Datumeinde</span>
          </dt>
          <dd>
            {tariefEntity.datumeinde ? <TextFormat value={tariefEntity.datumeinde} type="date" format={APP_LOCAL_DATE_FORMAT} /> : null}
          </dd>
          <dt>
            <span id="datumstart">Datumstart</span>
          </dt>
          <dd>
            {tariefEntity.datumstart ? <TextFormat value={tariefEntity.datumstart} type="date" format={APP_LOCAL_DATE_FORMAT} /> : null}
          </dd>
          <dt>
            <span id="eenheid">Eenheid</span>
          </dt>
          <dd>{tariefEntity.eenheid}</dd>
          <dt>
            <span id="wet">Wet</span>
          </dt>
          <dd>{tariefEntity.wet}</dd>
          <dt>Heeft Leverancier</dt>
          <dd>{tariefEntity.heeftLeverancier ? tariefEntity.heeftLeverancier.id : ''}</dd>
          <dt>Bevat Contract</dt>
          <dd>{tariefEntity.bevatContract ? tariefEntity.bevatContract.id : ''}</dd>
          <dt>Heeft Voorziening</dt>
          <dd>{tariefEntity.heeftVoorziening ? tariefEntity.heeftVoorziening.id : ''}</dd>
        </dl>
        <Button tag={Link} to="/tarief" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" /> <span className="d-none d-md-inline">Back</span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/tarief/${tariefEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
        </Button>
      </Col>
    </Row>
  );
};

export default TariefDetail;
