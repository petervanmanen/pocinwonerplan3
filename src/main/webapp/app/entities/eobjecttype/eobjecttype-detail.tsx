import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './eobjecttype.reducer';

export const EobjecttypeDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const eobjecttypeEntity = useAppSelector(state => state.eobjecttype.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="eobjecttypeDetailsHeading">Eobjecttype</h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="datumopname">Datumopname</span>
          </dt>
          <dd>
            {eobjecttypeEntity.datumopname ? (
              <TextFormat value={eobjecttypeEntity.datumopname} type="date" format={APP_LOCAL_DATE_FORMAT} />
            ) : null}
          </dd>
          <dt>
            <span id="definitie">Definitie</span>
          </dt>
          <dd>{eobjecttypeEntity.definitie}</dd>
          <dt>
            <span id="eaguid">Eaguid</span>
          </dt>
          <dd>{eobjecttypeEntity.eaguid}</dd>
          <dt>
            <span id="herkomst">Herkomst</span>
          </dt>
          <dd>{eobjecttypeEntity.herkomst}</dd>
          <dt>
            <span id="herkomstdefinitie">Herkomstdefinitie</span>
          </dt>
          <dd>{eobjecttypeEntity.herkomstdefinitie}</dd>
          <dt>
            <span id="id">Id</span>
          </dt>
          <dd>{eobjecttypeEntity.id}</dd>
          <dt>
            <span id="indicatieabstract">Indicatieabstract</span>
          </dt>
          <dd>{eobjecttypeEntity.indicatieabstract ? 'true' : 'false'}</dd>
          <dt>
            <span id="kwaliteit">Kwaliteit</span>
          </dt>
          <dd>{eobjecttypeEntity.kwaliteit}</dd>
          <dt>
            <span id="naam">Naam</span>
          </dt>
          <dd>{eobjecttypeEntity.naam}</dd>
          <dt>
            <span id="populatie">Populatie</span>
          </dt>
          <dd>{eobjecttypeEntity.populatie}</dd>
          <dt>
            <span id="stereotype">Stereotype</span>
          </dt>
          <dd>{eobjecttypeEntity.stereotype}</dd>
          <dt>
            <span id="toelichting">Toelichting</span>
          </dt>
          <dd>{eobjecttypeEntity.toelichting}</dd>
          <dt>
            <span id="uniekeaanduiding">Uniekeaanduiding</span>
          </dt>
          <dd>{eobjecttypeEntity.uniekeaanduiding}</dd>
        </dl>
        <Button tag={Link} to="/eobjecttype" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" /> <span className="d-none d-md-inline">Back</span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/eobjecttype/${eobjecttypeEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
        </Button>
      </Col>
    </Row>
  );
};

export default EobjecttypeDetail;
