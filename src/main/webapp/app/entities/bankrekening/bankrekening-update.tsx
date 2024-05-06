import React, { useState, useEffect } from 'react';
import { Link, useNavigate, useParams } from 'react-router-dom';
import { Button, Row, Col, FormText } from 'reactstrap';
import { isNumber, ValidatedField, ValidatedForm } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { IBankrekening } from 'app/shared/model/bankrekening.model';
import { getEntity, updateEntity, createEntity, reset } from './bankrekening.reducer';

export const BankrekeningUpdate = () => {
  const dispatch = useAppDispatch();

  const navigate = useNavigate();

  const { id } = useParams<'id'>();
  const isNew = id === undefined;

  const bankrekeningEntity = useAppSelector(state => state.bankrekening.entity);
  const loading = useAppSelector(state => state.bankrekening.loading);
  const updating = useAppSelector(state => state.bankrekening.updating);
  const updateSuccess = useAppSelector(state => state.bankrekening.updateSuccess);

  const handleClose = () => {
    navigate('/bankrekening');
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

    const entity = {
      ...bankrekeningEntity,
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
          ...bankrekeningEntity,
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="demo3App.bankrekening.home.createOrEditLabel" data-cy="BankrekeningCreateUpdateHeading">
            Create or edit a Bankrekening
          </h2>
        </Col>
      </Row>
      <Row className="justify-content-center">
        <Col md="8">
          {loading ? (
            <p>Loading...</p>
          ) : (
            <ValidatedForm defaultValues={defaultValues()} onSubmit={saveEntity}>
              {!isNew ? <ValidatedField name="id" required readOnly id="bankrekening-id" label="ID" validate={{ required: true }} /> : null}
              <ValidatedField label="Bank" id="bankrekening-bank" name="bank" data-cy="bank" type="text" />
              <ValidatedField label="Nummer" id="bankrekening-nummer" name="nummer" data-cy="nummer" type="text" />
              <ValidatedField
                label="Tennaamstelling"
                id="bankrekening-tennaamstelling"
                name="tennaamstelling"
                data-cy="tennaamstelling"
                type="text"
              />
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/bankrekening" replace color="info">
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

export default BankrekeningUpdate;
