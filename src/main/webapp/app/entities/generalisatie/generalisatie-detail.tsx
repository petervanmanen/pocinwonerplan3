import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './generalisatie.reducer';

export const GeneralisatieDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const generalisatieEntity = useAppSelector(state => state.generalisatie.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="generalisatieDetailsHeading">Generalisatie</h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="datumopname">Datumopname</span>
          </dt>
          <dd>
            {generalisatieEntity.datumopname ? (
              <TextFormat value={generalisatieEntity.datumopname} type="date" format={APP_LOCAL_DATE_FORMAT} />
            ) : null}
          </dd>
          <dt>
            <span id="definitie">Definitie</span>
          </dt>
          <dd>{generalisatieEntity.definitie}</dd>
          <dt>
            <span id="eaguid">Eaguid</span>
          </dt>
          <dd>{generalisatieEntity.eaguid}</dd>
          <dt>
            <span id="herkomst">Herkomst</span>
          </dt>
          <dd>{generalisatieEntity.herkomst}</dd>
          <dt>
            <span id="herkomstdefinitie">Herkomstdefinitie</span>
          </dt>
          <dd>{generalisatieEntity.herkomstdefinitie}</dd>
          <dt>
            <span id="id">Id</span>
          </dt>
          <dd>{generalisatieEntity.id}</dd>
          <dt>
            <span id="indicatiematerielehistorie">Indicatiematerielehistorie</span>
          </dt>
          <dd>{generalisatieEntity.indicatiematerielehistorie ? 'true' : 'false'}</dd>
          <dt>
            <span id="naam">Naam</span>
          </dt>
          <dd>{generalisatieEntity.naam}</dd>
          <dt>
            <span id="toelichting">Toelichting</span>
          </dt>
          <dd>{generalisatieEntity.toelichting}</dd>
        </dl>
        <Button tag={Link} to="/generalisatie" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" /> <span className="d-none d-md-inline">Back</span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/generalisatie/${generalisatieEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
        </Button>
      </Col>
    </Row>
  );
};

export default GeneralisatieDetail;
