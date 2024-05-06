import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import {} from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './opleiding.reducer';

export const OpleidingDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const opleidingEntity = useAppSelector(state => state.opleiding.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="opleidingDetailsHeading">Opleiding</h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">ID</span>
          </dt>
          <dd>{opleidingEntity.id}</dd>
          <dt>
            <span id="instituut">Instituut</span>
          </dt>
          <dd>{opleidingEntity.instituut}</dd>
          <dt>
            <span id="naam">Naam</span>
          </dt>
          <dd>{opleidingEntity.naam}</dd>
          <dt>
            <span id="omschrijving">Omschrijving</span>
          </dt>
          <dd>{opleidingEntity.omschrijving}</dd>
          <dt>
            <span id="prijs">Prijs</span>
          </dt>
          <dd>{opleidingEntity.prijs}</dd>
          <dt>Wordtgegevendoor Onderwijsinstituut</dt>
          <dd>
            {opleidingEntity.wordtgegevendoorOnderwijsinstituuts
              ? opleidingEntity.wordtgegevendoorOnderwijsinstituuts.map((val, i) => (
                  <span key={val.id}>
                    <a>{val.id}</a>
                    {opleidingEntity.wordtgegevendoorOnderwijsinstituuts &&
                    i === opleidingEntity.wordtgegevendoorOnderwijsinstituuts.length - 1
                      ? ''
                      : ', '}
                  </span>
                ))
              : null}
          </dd>
        </dl>
        <Button tag={Link} to="/opleiding" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" /> <span className="d-none d-md-inline">Back</span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/opleiding/${opleidingEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
        </Button>
      </Col>
    </Row>
  );
};

export default OpleidingDetail;
