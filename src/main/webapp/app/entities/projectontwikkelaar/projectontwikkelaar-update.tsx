import React, { useState, useEffect } from 'react';
import { Link, useNavigate, useParams } from 'react-router-dom';
import { Button, Row, Col, FormText } from 'reactstrap';
import { isNumber, ValidatedField, ValidatedForm } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { IPlan } from 'app/shared/model/plan.model';
import { getEntities as getPlans } from 'app/entities/plan/plan.reducer';
import { IProjectontwikkelaar } from 'app/shared/model/projectontwikkelaar.model';
import { getEntity, updateEntity, createEntity, reset } from './projectontwikkelaar.reducer';

export const ProjectontwikkelaarUpdate = () => {
  const dispatch = useAppDispatch();

  const navigate = useNavigate();

  const { id } = useParams<'id'>();
  const isNew = id === undefined;

  const plans = useAppSelector(state => state.plan.entities);
  const projectontwikkelaarEntity = useAppSelector(state => state.projectontwikkelaar.entity);
  const loading = useAppSelector(state => state.projectontwikkelaar.loading);
  const updating = useAppSelector(state => state.projectontwikkelaar.updating);
  const updateSuccess = useAppSelector(state => state.projectontwikkelaar.updateSuccess);

  const handleClose = () => {
    navigate('/projectontwikkelaar');
  };

  useEffect(() => {
    if (isNew) {
      dispatch(reset());
    } else {
      dispatch(getEntity(id));
    }

    dispatch(getPlans({}));
  }, []);

  useEffect(() => {
    if (updateSuccess) {
      handleClose();
    }
  }, [updateSuccess]);

  // eslint-disable-next-line complexity
  const saveEntity = values => {
    if (values.id !== undefined && typeof values.id !== 'number') {
      values.id = Number(values.id);
    }

    const entity = {
      ...projectontwikkelaarEntity,
      ...values,
      heeftPlans: mapIdList(values.heeftPlans),
    };

    if (isNew) {
      dispatch(createEntity(entity));
    } else {
      dispatch(updateEntity(entity));
    }
  };

  const defaultValues = () =>
    isNew
      ? {}
      : {
          ...projectontwikkelaarEntity,
          heeftPlans: projectontwikkelaarEntity?.heeftPlans?.map(e => e.id.toString()),
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="demo3App.projectontwikkelaar.home.createOrEditLabel" data-cy="ProjectontwikkelaarCreateUpdateHeading">
            Create or edit a Projectontwikkelaar
          </h2>
        </Col>
      </Row>
      <Row className="justify-content-center">
        <Col md="8">
          {loading ? (
            <p>Loading...</p>
          ) : (
            <ValidatedForm defaultValues={defaultValues()} onSubmit={saveEntity}>
              {!isNew ? (
                <ValidatedField name="id" required readOnly id="projectontwikkelaar-id" label="ID" validate={{ required: true }} />
              ) : null}
              <ValidatedField label="Adres" id="projectontwikkelaar-adres" name="adres" data-cy="adres" type="text" />
              <ValidatedField label="Naam" id="projectontwikkelaar-naam" name="naam" data-cy="naam" type="text" />
              <ValidatedField
                label="Heeft Plan"
                id="projectontwikkelaar-heeftPlan"
                data-cy="heeftPlan"
                type="select"
                multiple
                name="heeftPlans"
              >
                <option value="" key="0" />
                {plans
                  ? plans.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.id}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/projectontwikkelaar" replace color="info">
                <FontAwesomeIcon icon="arrow-left" />
                &nbsp;
                <span className="d-none d-md-inline">Back</span>
              </Button>
              &nbsp;
              <Button color="primary" id="save-entity" data-cy="entityCreateSaveButton" type="submit" disabled={updating}>
                <FontAwesomeIcon icon="save" />
                &nbsp; Save
              </Button>
            </ValidatedForm>
          )}
        </Col>
      </Row>
    </div>
  );
};

export default ProjectontwikkelaarUpdate;
