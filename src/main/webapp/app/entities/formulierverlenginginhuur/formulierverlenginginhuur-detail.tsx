import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './formulierverlenginginhuur.reducer';

export const FormulierverlenginginhuurDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const formulierverlenginginhuurEntity = useAppSelector(state => state.formulierverlenginginhuur.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="formulierverlenginginhuurDetailsHeading">Formulierverlenginginhuur</h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">ID</span>
          </dt>
          <dd>{formulierverlenginginhuurEntity.id}</dd>
          <dt>
            <span id="datumeindenieuw">Datumeindenieuw</span>
          </dt>
          <dd>
            {formulierverlenginginhuurEntity.datumeindenieuw ? (
              <TextFormat value={formulierverlenginginhuurEntity.datumeindenieuw} type="date" format={APP_LOCAL_DATE_FORMAT} />
            ) : null}
          </dd>
          <dt>
            <span id="indicatieredeninhuurgewijzigd">Indicatieredeninhuurgewijzigd</span>
          </dt>
          <dd>{formulierverlenginginhuurEntity.indicatieredeninhuurgewijzigd}</dd>
          <dt>
            <span id="indicatieverhogeninkooporder">Indicatieverhogeninkooporder</span>
          </dt>
          <dd>{formulierverlenginginhuurEntity.indicatieverhogeninkooporder}</dd>
          <dt>
            <span id="toelichting">Toelichting</span>
          </dt>
          <dd>{formulierverlenginginhuurEntity.toelichting}</dd>
        </dl>
        <Button tag={Link} to="/formulierverlenginginhuur" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" /> <span className="d-none d-md-inline">Back</span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/formulierverlenginginhuur/${formulierverlenginginhuurEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
        </Button>
      </Col>
    </Row>
  );
};

export default FormulierverlenginginhuurDetail;
