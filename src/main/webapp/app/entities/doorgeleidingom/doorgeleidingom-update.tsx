import React, { useState, useEffect } from 'react';
import { Link, useNavigate, useParams } from 'react-router-dom';
import { Button, Row, Col, FormText } from 'reactstrap';
import { isNumber, ValidatedField, ValidatedForm } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { IDoorgeleidingom } from 'app/shared/model/doorgeleidingom.model';
import { getEntity, updateEntity, createEntity, reset } from './doorgeleidingom.reducer';

export const DoorgeleidingomUpdate = () => {
  const dispatch = useAppDispatch();

  const navigate = useNavigate();

  const { id } = useParams<'id'>();
  const isNew = id === undefined;

  const doorgeleidingomEntity = useAppSelector(state => state.doorgeleidingom.entity);
  const loading = useAppSelector(state => state.doorgeleidingom.loading);
  const updating = useAppSelector(state => state.doorgeleidingom.updating);
  const updateSuccess = useAppSelector(state => state.doorgeleidingom.updateSuccess);

  const handleClose = () => {
    navigate('/doorgeleidingom');
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
      ...doorgeleidingomEntity,
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
          ...doorgeleidingomEntity,
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="demo3App.doorgeleidingom.home.createOrEditLabel" data-cy="DoorgeleidingomCreateUpdateHeading">
            Create or edit a Doorgeleidingom
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
                <ValidatedField name="id" required readOnly id="doorgeleidingom-id" label="ID" validate={{ required: true }} />
              ) : null}
              <ValidatedField label="Afdoening" id="doorgeleidingom-afdoening" name="afdoening" data-cy="afdoening" type="text" />
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/doorgeleidingom" replace color="info">
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

export default DoorgeleidingomUpdate;
