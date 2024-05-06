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
import { IOmgevingsvergunning } from 'app/shared/model/omgevingsvergunning.model';
import { getEntity, updateEntity, createEntity, reset } from './omgevingsvergunning.reducer';

export const OmgevingsvergunningUpdate = () => {
  const dispatch = useAppDispatch();

  const navigate = useNavigate();

  const { id } = useParams<'id'>();
  const isNew = id === undefined;

  const plans = useAppSelector(state => state.plan.entities);
  const omgevingsvergunningEntity = useAppSelector(state => state.omgevingsvergunning.entity);
  const loading = useAppSelector(state => state.omgevingsvergunning.loading);
  const updating = useAppSelector(state => state.omgevingsvergunning.updating);
  const updateSuccess = useAppSelector(state => state.omgevingsvergunning.updateSuccess);

  const handleClose = () => {
    navigate('/omgevingsvergunning');
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
      ...omgevingsvergunningEntity,
      ...values,
      betrekkingopPlan: plans.find(it => it.id.toString() === values.betrekkingopPlan?.toString()),
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
          ...omgevingsvergunningEntity,
          betrekkingopPlan: omgevingsvergunningEntity?.betrekkingopPlan?.id,
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="demo3App.omgevingsvergunning.home.createOrEditLabel" data-cy="OmgevingsvergunningCreateUpdateHeading">
            Create or edit a Omgevingsvergunning
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
                <ValidatedField name="id" required readOnly id="omgevingsvergunning-id" label="ID" validate={{ required: true }} />
              ) : null}
              <ValidatedField
                id="omgevingsvergunning-betrekkingopPlan"
                name="betrekkingopPlan"
                data-cy="betrekkingopPlan"
                label="Betrekkingop Plan"
                type="select"
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
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/omgevingsvergunning" replace color="info">
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

export default OmgevingsvergunningUpdate;
