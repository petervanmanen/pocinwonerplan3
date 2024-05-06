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
import { IGebouw } from 'app/shared/model/gebouw.model';
import { getEntity, updateEntity, createEntity, reset } from './gebouw.reducer';

export const GebouwUpdate = () => {
  const dispatch = useAppDispatch();

  const navigate = useNavigate();

  const { id } = useParams<'id'>();
  const isNew = id === undefined;

  const plans = useAppSelector(state => state.plan.entities);
  const gebouwEntity = useAppSelector(state => state.gebouw.entity);
  const loading = useAppSelector(state => state.gebouw.loading);
  const updating = useAppSelector(state => state.gebouw.updating);
  const updateSuccess = useAppSelector(state => state.gebouw.updateSuccess);

  const handleClose = () => {
    navigate('/gebouw');
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
      ...gebouwEntity,
      ...values,
      bestaatuitPlan: plans.find(it => it.id.toString() === values.bestaatuitPlan?.toString()),
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
          ...gebouwEntity,
          bestaatuitPlan: gebouwEntity?.bestaatuitPlan?.id,
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="demo3App.gebouw.home.createOrEditLabel" data-cy="GebouwCreateUpdateHeading">
            Create or edit a Gebouw
          </h2>
        </Col>
      </Row>
      <Row className="justify-content-center">
        <Col md="8">
          {loading ? (
            <p>Loading...</p>
          ) : (
            <ValidatedForm defaultValues={defaultValues()} onSubmit={saveEntity}>
              {!isNew ? <ValidatedField name="id" required readOnly id="gebouw-id" label="ID" validate={{ required: true }} /> : null}
              <ValidatedField label="Aantal" id="gebouw-aantal" name="aantal" data-cy="aantal" type="text" />
              <ValidatedField
                label="Aantaladressen"
                id="gebouw-aantaladressen"
                name="aantaladressen"
                data-cy="aantaladressen"
                type="text"
              />
              <ValidatedField label="Aantalkamers" id="gebouw-aantalkamers" name="aantalkamers" data-cy="aantalkamers" type="text" />
              <ValidatedField label="Aardgasloos" id="gebouw-aardgasloos" name="aardgasloos" data-cy="aardgasloos" check type="checkbox" />
              <ValidatedField label="Duurzaam" id="gebouw-duurzaam" name="duurzaam" data-cy="duurzaam" check type="checkbox" />
              <ValidatedField label="Energielabel" id="gebouw-energielabel" name="energielabel" data-cy="energielabel" type="text" />
              <ValidatedField
                label="Natuurinclusief"
                id="gebouw-natuurinclusief"
                name="natuurinclusief"
                data-cy="natuurinclusief"
                check
                type="checkbox"
              />
              <ValidatedField label="Oppervlakte" id="gebouw-oppervlakte" name="oppervlakte" data-cy="oppervlakte" type="text" />
              <ValidatedField label="Regenwater" id="gebouw-regenwater" name="regenwater" data-cy="regenwater" check type="checkbox" />
              <ValidatedField
                id="gebouw-bestaatuitPlan"
                name="bestaatuitPlan"
                data-cy="bestaatuitPlan"
                label="Bestaatuit Plan"
                type="select"
                required
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
              <FormText>This field is required.</FormText>
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/gebouw" replace color="info">
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

export default GebouwUpdate;
