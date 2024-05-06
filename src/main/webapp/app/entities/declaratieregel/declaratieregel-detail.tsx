import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './declaratieregel.reducer';

export const DeclaratieregelDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const declaratieregelEntity = useAppSelector(state => state.declaratieregel.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="declaratieregelDetailsHeading">Declaratieregel</h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">ID</span>
          </dt>
          <dd>{declaratieregelEntity.id}</dd>
          <dt>
            <span id="bedrag">Bedrag</span>
          </dt>
          <dd>{declaratieregelEntity.bedrag}</dd>
          <dt>
            <span id="code">Code</span>
          </dt>
          <dd>{declaratieregelEntity.code}</dd>
          <dt>
            <span id="datumeinde">Datumeinde</span>
          </dt>
          <dd>
            {declaratieregelEntity.datumeinde ? (
              <TextFormat value={declaratieregelEntity.datumeinde} type="date" format={APP_LOCAL_DATE_FORMAT} />
            ) : null}
          </dd>
          <dt>
            <span id="datumstart">Datumstart</span>
          </dt>
          <dd>
            {declaratieregelEntity.datumstart ? (
              <TextFormat value={declaratieregelEntity.datumstart} type="date" format={APP_LOCAL_DATE_FORMAT} />
            ) : null}
          </dd>
          <dt>Isvoor Beschikking</dt>
          <dd>{declaratieregelEntity.isvoorBeschikking ? declaratieregelEntity.isvoorBeschikking.id : ''}</dd>
          <dt>Betreft Client</dt>
          <dd>{declaratieregelEntity.betreftClient ? declaratieregelEntity.betreftClient.id : ''}</dd>
          <dt>Valtbinnen Declaratie</dt>
          <dd>{declaratieregelEntity.valtbinnenDeclaratie ? declaratieregelEntity.valtbinnenDeclaratie.id : ''}</dd>
          <dt>Isopbasisvan Toewijzing</dt>
          <dd>{declaratieregelEntity.isopbasisvanToewijzing ? declaratieregelEntity.isopbasisvanToewijzing.id : ''}</dd>
        </dl>
        <Button tag={Link} to="/declaratieregel" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" /> <span className="d-none d-md-inline">Back</span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/declaratieregel/${declaratieregelEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
        </Button>
      </Col>
    </Row>
  );
};

export default DeclaratieregelDetail;
