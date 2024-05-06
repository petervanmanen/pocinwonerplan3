import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './lstclass-1.reducer';

export const Lstclass1Detail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const lstclass1Entity = useAppSelector(state => state.lstclass1.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="lstclass1DetailsHeading">Lstclass 1</h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">ID</span>
          </dt>
          <dd>{lstclass1Entity.id}</dd>
          <dt>
            <span id="waarde">Waarde</span>
          </dt>
          <dd>{lstclass1Entity.waarde}</dd>
          <dt>
            <span id="dwhrecordid">Dwhrecordid</span>
          </dt>
          <dd>{lstclass1Entity.dwhrecordid}</dd>
          <dt>
            <span id="dwhodsrecordid">Dwhodsrecordid</span>
          </dt>
          <dd>{lstclass1Entity.dwhodsrecordid}</dd>
          <dt>
            <span id="dwhstartdt">Dwhstartdt</span>
          </dt>
          <dd>
            {lstclass1Entity.dwhstartdt ? (
              <TextFormat value={lstclass1Entity.dwhstartdt} type="date" format={APP_LOCAL_DATE_FORMAT} />
            ) : null}
          </dd>
          <dt>
            <span id="dwheinddt">Dwheinddt</span>
          </dt>
          <dd>
            {lstclass1Entity.dwheinddt ? <TextFormat value={lstclass1Entity.dwheinddt} type="date" format={APP_LOCAL_DATE_FORMAT} /> : null}
          </dd>
          <dt>
            <span id="dwhrunid">Dwhrunid</span>
          </dt>
          <dd>{lstclass1Entity.dwhrunid}</dd>
          <dt>
            <span id="dwhbron">Dwhbron</span>
          </dt>
          <dd>{lstclass1Entity.dwhbron}</dd>
          <dt>
            <span id="dwhlaaddt">Dwhlaaddt</span>
          </dt>
          <dd>
            {lstclass1Entity.dwhlaaddt ? <TextFormat value={lstclass1Entity.dwhlaaddt} type="date" format={APP_LOCAL_DATE_FORMAT} /> : null}
          </dd>
          <dt>
            <span id="dwhactueel">Dwhactueel</span>
          </dt>
          <dd>{lstclass1Entity.dwhactueel}</dd>
          <dt>
            <span id="lstclass1id">Lstclass 1 Id</span>
          </dt>
          <dd>{lstclass1Entity.lstclass1id}</dd>
        </dl>
        <Button tag={Link} to="/lstclass-1" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" /> <span className="d-none d-md-inline">Back</span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/lstclass-1/${lstclass1Entity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
        </Button>
      </Col>
    </Row>
  );
};

export default Lstclass1Detail;
