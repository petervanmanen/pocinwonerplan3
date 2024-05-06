import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import {} from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './ordeningsschema.reducer';

export const OrdeningsschemaDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const ordeningsschemaEntity = useAppSelector(state => state.ordeningsschema.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="ordeningsschemaDetailsHeading">Ordeningsschema</h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">ID</span>
          </dt>
          <dd>{ordeningsschemaEntity.id}</dd>
          <dt>
            <span id="naam">Naam</span>
          </dt>
          <dd>{ordeningsschemaEntity.naam}</dd>
          <dt>
            <span id="text">Text</span>
          </dt>
          <dd>{ordeningsschemaEntity.text}</dd>
          <dt>Heeft Archiefstuk</dt>
          <dd>
            {ordeningsschemaEntity.heeftArchiefstuks
              ? ordeningsschemaEntity.heeftArchiefstuks.map((val, i) => (
                  <span key={val.id}>
                    <a>{val.id}</a>
                    {ordeningsschemaEntity.heeftArchiefstuks && i === ordeningsschemaEntity.heeftArchiefstuks.length - 1 ? '' : ', '}
                  </span>
                ))
              : null}
          </dd>
        </dl>
        <Button tag={Link} to="/ordeningsschema" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" /> <span className="d-none d-md-inline">Back</span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/ordeningsschema/${ordeningsschemaEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
        </Button>
      </Col>
    </Row>
  );
};

export default OrdeningsschemaDetail;
