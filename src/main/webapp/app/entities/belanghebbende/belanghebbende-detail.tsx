import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './belanghebbende.reducer';

export const BelanghebbendeDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const belanghebbendeEntity = useAppSelector(state => state.belanghebbende.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="belanghebbendeDetailsHeading">Belanghebbende</h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">ID</span>
          </dt>
          <dd>{belanghebbendeEntity.id}</dd>
          <dt>
            <span id="datumstart">Datumstart</span>
          </dt>
          <dd>
            {belanghebbendeEntity.datumstart ? (
              <TextFormat value={belanghebbendeEntity.datumstart} type="date" format={APP_LOCAL_DATE_FORMAT} />
            ) : null}
          </dd>
          <dt>
            <span id="datumtot">Datumtot</span>
          </dt>
          <dd>
            {belanghebbendeEntity.datumtot ? (
              <TextFormat value={belanghebbendeEntity.datumtot} type="date" format={APP_LOCAL_DATE_FORMAT} />
            ) : null}
          </dd>
          <dt>Heeft Museumobject</dt>
          <dd>
            {belanghebbendeEntity.heeftMuseumobjects
              ? belanghebbendeEntity.heeftMuseumobjects.map((val, i) => (
                  <span key={val.id}>
                    <a>{val.id}</a>
                    {belanghebbendeEntity.heeftMuseumobjects && i === belanghebbendeEntity.heeftMuseumobjects.length - 1 ? '' : ', '}
                  </span>
                ))
              : null}
          </dd>
        </dl>
        <Button tag={Link} to="/belanghebbende" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" /> <span className="d-none d-md-inline">Back</span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/belanghebbende/${belanghebbendeEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
        </Button>
      </Col>
    </Row>
  );
};

export default BelanghebbendeDetail;
