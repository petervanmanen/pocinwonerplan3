import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './attribuutsoort.reducer';

export const AttribuutsoortDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const attribuutsoortEntity = useAppSelector(state => state.attribuutsoort.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="attribuutsoortDetailsHeading">Attribuutsoort</h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="authentiek">Authentiek</span>
          </dt>
          <dd>{attribuutsoortEntity.authentiek ? 'true' : 'false'}</dd>
          <dt>
            <span id="datumopname">Datumopname</span>
          </dt>
          <dd>
            {attribuutsoortEntity.datumopname ? (
              <TextFormat value={attribuutsoortEntity.datumopname} type="date" format={APP_LOCAL_DATE_FORMAT} />
            ) : null}
          </dd>
          <dt>
            <span id="definitie">Definitie</span>
          </dt>
          <dd>{attribuutsoortEntity.definitie}</dd>
          <dt>
            <span id="domein">Domein</span>
          </dt>
          <dd>{attribuutsoortEntity.domein}</dd>
          <dt>
            <span id="eaguid">Eaguid</span>
          </dt>
          <dd>{attribuutsoortEntity.eaguid}</dd>
          <dt>
            <span id="herkomst">Herkomst</span>
          </dt>
          <dd>{attribuutsoortEntity.herkomst}</dd>
          <dt>
            <span id="herkomstdefinitie">Herkomstdefinitie</span>
          </dt>
          <dd>{attribuutsoortEntity.herkomstdefinitie}</dd>
          <dt>
            <span id="id">Id</span>
          </dt>
          <dd>{attribuutsoortEntity.id}</dd>
          <dt>
            <span id="identificerend">Identificerend</span>
          </dt>
          <dd>{attribuutsoortEntity.identificerend ? 'true' : 'false'}</dd>
          <dt>
            <span id="indicatieafleidbaar">Indicatieafleidbaar</span>
          </dt>
          <dd>{attribuutsoortEntity.indicatieafleidbaar ? 'true' : 'false'}</dd>
          <dt>
            <span id="indicatiematerielehistorie">Indicatiematerielehistorie</span>
          </dt>
          <dd>{attribuutsoortEntity.indicatiematerielehistorie ? 'true' : 'false'}</dd>
          <dt>
            <span id="kardinaliteit">Kardinaliteit</span>
          </dt>
          <dd>{attribuutsoortEntity.kardinaliteit}</dd>
          <dt>
            <span id="lengte">Lengte</span>
          </dt>
          <dd>{attribuutsoortEntity.lengte}</dd>
          <dt>
            <span id="mogelijkgeenwaarde">Mogelijkgeenwaarde</span>
          </dt>
          <dd>{attribuutsoortEntity.mogelijkgeenwaarde ? 'true' : 'false'}</dd>
          <dt>
            <span id="naam">Naam</span>
          </dt>
          <dd>{attribuutsoortEntity.naam}</dd>
          <dt>
            <span id="patroon">Patroon</span>
          </dt>
          <dd>{attribuutsoortEntity.patroon}</dd>
          <dt>
            <span id="precisie">Precisie</span>
          </dt>
          <dd>{attribuutsoortEntity.precisie}</dd>
          <dt>
            <span id="stereotype">Stereotype</span>
          </dt>
          <dd>{attribuutsoortEntity.stereotype}</dd>
          <dt>
            <span id="toelichting">Toelichting</span>
          </dt>
          <dd>{attribuutsoortEntity.toelichting}</dd>
          <dt>Heeft Datatype</dt>
          <dd>{attribuutsoortEntity.heeftDatatype ? attribuutsoortEntity.heeftDatatype.id : ''}</dd>
        </dl>
        <Button tag={Link} to="/attribuutsoort" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" /> <span className="d-none d-md-inline">Back</span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/attribuutsoort/${attribuutsoortEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
        </Button>
      </Col>
    </Row>
  );
};

export default AttribuutsoortDetail;
