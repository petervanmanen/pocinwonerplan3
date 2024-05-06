import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import {} from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './formuliersoort.reducer';

export const FormuliersoortDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const formuliersoortEntity = useAppSelector(state => state.formuliersoort.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="formuliersoortDetailsHeading">Formuliersoort</h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">ID</span>
          </dt>
          <dd>{formuliersoortEntity.id}</dd>
          <dt>
            <span id="ingebruik">Ingebruik</span>
          </dt>
          <dd>{formuliersoortEntity.ingebruik}</dd>
          <dt>
            <span id="naam">Naam</span>
          </dt>
          <dd>{formuliersoortEntity.naam}</dd>
          <dt>
            <span id="onderwerp">Onderwerp</span>
          </dt>
          <dd>{formuliersoortEntity.onderwerp}</dd>
          <dt>Isaanleidingvoor Zaaktype</dt>
          <dd>
            {formuliersoortEntity.isaanleidingvoorZaaktypes
              ? formuliersoortEntity.isaanleidingvoorZaaktypes.map((val, i) => (
                  <span key={val.id}>
                    <a>{val.id}</a>
                    {formuliersoortEntity.isaanleidingvoorZaaktypes && i === formuliersoortEntity.isaanleidingvoorZaaktypes.length - 1
                      ? ''
                      : ', '}
                  </span>
                ))
              : null}
          </dd>
        </dl>
        <Button tag={Link} to="/formuliersoort" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" /> <span className="d-none d-md-inline">Back</span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/formuliersoort/${formuliersoortEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
        </Button>
      </Col>
    </Row>
  );
};

export default FormuliersoortDetail;
