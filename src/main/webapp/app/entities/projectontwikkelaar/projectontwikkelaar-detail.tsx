import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import {} from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './projectontwikkelaar.reducer';

export const ProjectontwikkelaarDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const projectontwikkelaarEntity = useAppSelector(state => state.projectontwikkelaar.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="projectontwikkelaarDetailsHeading">Projectontwikkelaar</h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">ID</span>
          </dt>
          <dd>{projectontwikkelaarEntity.id}</dd>
          <dt>
            <span id="adres">Adres</span>
          </dt>
          <dd>{projectontwikkelaarEntity.adres}</dd>
          <dt>
            <span id="naam">Naam</span>
          </dt>
          <dd>{projectontwikkelaarEntity.naam}</dd>
          <dt>Heeft Plan</dt>
          <dd>
            {projectontwikkelaarEntity.heeftPlans
              ? projectontwikkelaarEntity.heeftPlans.map((val, i) => (
                  <span key={val.id}>
                    <a>{val.id}</a>
                    {projectontwikkelaarEntity.heeftPlans && i === projectontwikkelaarEntity.heeftPlans.length - 1 ? '' : ', '}
                  </span>
                ))
              : null}
          </dd>
        </dl>
        <Button tag={Link} to="/projectontwikkelaar" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" /> <span className="d-none d-md-inline">Back</span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/projectontwikkelaar/${projectontwikkelaarEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
        </Button>
      </Col>
    </Row>
  );
};

export default ProjectontwikkelaarDetail;
