import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './plan.reducer';

export const PlanDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const planEntity = useAppSelector(state => state.plan.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="planDetailsHeading">Plan</h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">ID</span>
          </dt>
          <dd>{planEntity.id}</dd>
          <dt>
            <span id="zeventigprocentverkocht">Zeventigprocentverkocht</span>
          </dt>
          <dd>{planEntity.zeventigprocentverkocht ? 'true' : 'false'}</dd>
          <dt>
            <span id="aardgasloos">Aardgasloos</span>
          </dt>
          <dd>{planEntity.aardgasloos ? 'true' : 'false'}</dd>
          <dt>
            <span id="bestemminggoedgekeurd">Bestemminggoedgekeurd</span>
          </dt>
          <dd>{planEntity.bestemminggoedgekeurd ? 'true' : 'false'}</dd>
          <dt>
            <span id="eersteoplevering">Eersteoplevering</span>
          </dt>
          <dd>
            {planEntity.eersteoplevering ? (
              <TextFormat value={planEntity.eersteoplevering} type="date" format={APP_LOCAL_DATE_FORMAT} />
            ) : null}
          </dd>
          <dt>
            <span id="eigendomgemeente">Eigendomgemeente</span>
          </dt>
          <dd>{planEntity.eigendomgemeente ? 'true' : 'false'}</dd>
          <dt>
            <span id="gebiedstransformatie">Gebiedstransformatie</span>
          </dt>
          <dd>{planEntity.gebiedstransformatie ? 'true' : 'false'}</dd>
          <dt>
            <span id="intentie">Intentie</span>
          </dt>
          <dd>{planEntity.intentie ? 'true' : 'false'}</dd>
          <dt>
            <span id="laatsteoplevering">Laatsteoplevering</span>
          </dt>
          <dd>
            {planEntity.laatsteoplevering ? (
              <TextFormat value={planEntity.laatsteoplevering} type="date" format={APP_LOCAL_DATE_FORMAT} />
            ) : null}
          </dd>
          <dt>
            <span id="naam">Naam</span>
          </dt>
          <dd>{planEntity.naam}</dd>
          <dt>
            <span id="nummer">Nummer</span>
          </dt>
          <dd>{planEntity.nummer}</dd>
          <dt>
            <span id="onherroepelijk">Onherroepelijk</span>
          </dt>
          <dd>{planEntity.onherroepelijk ? 'true' : 'false'}</dd>
          <dt>
            <span id="percelen">Percelen</span>
          </dt>
          <dd>{planEntity.percelen}</dd>
          <dt>
            <span id="startbouw">Startbouw</span>
          </dt>
          <dd>{planEntity.startbouw ? <TextFormat value={planEntity.startbouw} type="date" format={APP_LOCAL_DATE_FORMAT} /> : null}</dd>
          <dt>
            <span id="startverkoop">Startverkoop</span>
          </dt>
          <dd>
            {planEntity.startverkoop ? <TextFormat value={planEntity.startverkoop} type="date" format={APP_LOCAL_DATE_FORMAT} /> : null}
          </dd>
          <dt>Binnenprogramma Programma</dt>
          <dd>{planEntity.binnenprogrammaProgramma ? planEntity.binnenprogrammaProgramma.id : ''}</dd>
          <dt>Isprojectleidervan Projectleider</dt>
          <dd>{planEntity.isprojectleidervanProjectleider ? planEntity.isprojectleidervanProjectleider.id : ''}</dd>
          <dt>Heeft Projectontwikkelaar</dt>
          <dd>
            {planEntity.heeftProjectontwikkelaars
              ? planEntity.heeftProjectontwikkelaars.map((val, i) => (
                  <span key={val.id}>
                    <a>{val.id}</a>
                    {planEntity.heeftProjectontwikkelaars && i === planEntity.heeftProjectontwikkelaars.length - 1 ? '' : ', '}
                  </span>
                ))
              : null}
          </dd>
        </dl>
        <Button tag={Link} to="/plan" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" /> <span className="d-none d-md-inline">Back</span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/plan/${planEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
        </Button>
      </Col>
    </Row>
  );
};

export default PlanDetail;
