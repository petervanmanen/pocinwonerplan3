import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import {} from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './stremming.reducer';

export const StremmingDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const stremmingEntity = useAppSelector(state => state.stremming.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="stremmingDetailsHeading">Stremming</h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">ID</span>
          </dt>
          <dd>{stremmingEntity.id}</dd>
          <dt>
            <span id="aantalgehinderden">Aantalgehinderden</span>
          </dt>
          <dd>{stremmingEntity.aantalgehinderden}</dd>
          <dt>
            <span id="datumaanmelding">Datumaanmelding</span>
          </dt>
          <dd>{stremmingEntity.datumaanmelding}</dd>
          <dt>
            <span id="datumeinde">Datumeinde</span>
          </dt>
          <dd>{stremmingEntity.datumeinde}</dd>
          <dt>
            <span id="datumstart">Datumstart</span>
          </dt>
          <dd>{stremmingEntity.datumstart}</dd>
          <dt>
            <span id="datumwijziging">Datumwijziging</span>
          </dt>
          <dd>{stremmingEntity.datumwijziging}</dd>
          <dt>
            <span id="delentoegestaan">Delentoegestaan</span>
          </dt>
          <dd>{stremmingEntity.delentoegestaan ? 'true' : 'false'}</dd>
          <dt>
            <span id="geschiktvoorpublicatie">Geschiktvoorpublicatie</span>
          </dt>
          <dd>{stremmingEntity.geschiktvoorpublicatie ? 'true' : 'false'}</dd>
          <dt>
            <span id="hinderklasse">Hinderklasse</span>
          </dt>
          <dd>{stremmingEntity.hinderklasse}</dd>
          <dt>
            <span id="locatie">Locatie</span>
          </dt>
          <dd>{stremmingEntity.locatie}</dd>
          <dt>
            <span id="naam">Naam</span>
          </dt>
          <dd>{stremmingEntity.naam}</dd>
          <dt>
            <span id="status">Status</span>
          </dt>
          <dd>{stremmingEntity.status}</dd>
          <dt>Betreft Wegdeel</dt>
          <dd>
            {stremmingEntity.betreftWegdeels
              ? stremmingEntity.betreftWegdeels.map((val, i) => (
                  <span key={val.id}>
                    <a>{val.id}</a>
                    {stremmingEntity.betreftWegdeels && i === stremmingEntity.betreftWegdeels.length - 1 ? '' : ', '}
                  </span>
                ))
              : null}
          </dd>
          <dt>Ingevoerddoor Medewerker</dt>
          <dd>{stremmingEntity.ingevoerddoorMedewerker ? stremmingEntity.ingevoerddoorMedewerker.id : ''}</dd>
          <dt>Gewijzigddoor Medewerker</dt>
          <dd>{stremmingEntity.gewijzigddoorMedewerker ? stremmingEntity.gewijzigddoorMedewerker.id : ''}</dd>
        </dl>
        <Button tag={Link} to="/stremming" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" /> <span className="d-none d-md-inline">Back</span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/stremming/${stremmingEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
        </Button>
      </Col>
    </Row>
  );
};

export default StremmingDetail;
