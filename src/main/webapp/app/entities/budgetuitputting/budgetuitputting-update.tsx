import React, { useState, useEffect } from 'react';
import { Link, useNavigate, useParams } from 'react-router-dom';
import { Button, Row, Col, FormText } from 'reactstrap';
import { isNumber, ValidatedField, ValidatedForm } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { IBudgetuitputting } from 'app/shared/model/budgetuitputting.model';
import { getEntity, updateEntity, createEntity, reset } from './budgetuitputting.reducer';

export const BudgetuitputtingUpdate = () => {
  const dispatch = useAppDispatch();

  const navigate = useNavigate();

  const { id } = useParams<'id'>();
  const isNew = id === undefined;

  const budgetuitputtingEntity = useAppSelector(state => state.budgetuitputting.entity);
  const loading = useAppSelector(state => state.budgetuitputting.loading);
  const updating = useAppSelector(state => state.budgetuitputting.updating);
  const updateSuccess = useAppSelector(state => state.budgetuitputting.updateSuccess);

  const handleClose = () => {
    navigate('/budgetuitputting');
  };

  useEffect(() => {
    if (isNew) {
      dispatch(reset());
    } else {
      dispatch(getEntity(id));
    }
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
    if (values.uitgenutbedrag !== undefined && typeof values.uitgenutbedrag !== 'number') {
      values.uitgenutbedrag = Number(values.uitgenutbedrag);
    }

    const entity = {
      ...budgetuitputtingEntity,
      ...values,
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
          ...budgetuitputtingEntity,
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="demo3App.budgetuitputting.home.createOrEditLabel" data-cy="BudgetuitputtingCreateUpdateHeading">
            Create or edit a Budgetuitputting
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
                <ValidatedField name="id" required readOnly id="budgetuitputting-id" label="ID" validate={{ required: true }} />
              ) : null}
              <ValidatedField label="Datum" id="budgetuitputting-datum" name="datum" data-cy="datum" type="date" />
              <ValidatedField
                label="Uitgenutbedrag"
                id="budgetuitputting-uitgenutbedrag"
                name="uitgenutbedrag"
                data-cy="uitgenutbedrag"
                type="text"
              />
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/budgetuitputting" replace color="info">
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

export default BudgetuitputtingUpdate;
