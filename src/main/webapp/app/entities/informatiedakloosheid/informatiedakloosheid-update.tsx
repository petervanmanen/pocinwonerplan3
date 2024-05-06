import React, { useState, useEffect } from 'react';
import { Link, useNavigate, useParams } from 'react-router-dom';
import { Button, Row, Col, FormText } from 'reactstrap';
import { isNumber, ValidatedField, ValidatedForm } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { IInformatiedakloosheid } from 'app/shared/model/informatiedakloosheid.model';
import { getEntity, updateEntity, createEntity, reset } from './informatiedakloosheid.reducer';

export const InformatiedakloosheidUpdate = () => {
  const dispatch = useAppDispatch();

  const navigate = useNavigate();

  const { id } = useParams<'id'>();
  const isNew = id === undefined;

  const informatiedakloosheidEntity = useAppSelector(state => state.informatiedakloosheid.entity);
  const loading = useAppSelector(state => state.informatiedakloosheid.loading);
  const updating = useAppSelector(state => state.informatiedakloosheid.updating);
  const updateSuccess = useAppSelector(state => state.informatiedakloosheid.updateSuccess);

  const handleClose = () => {
    navigate('/informatiedakloosheid');
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
      ...informatiedakloosheidEntity,
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
          ...informatiedakloosheidEntity,
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="demo3App.informatiedakloosheid.home.createOrEditLabel" data-cy="InformatiedakloosheidCreateUpdateHeading">
            Create or edit a Informatiedakloosheid
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
                <ValidatedField name="id" required readOnly id="informatiedakloosheid-id" label="ID" validate={{ required: true }} />
              ) : null}
              <ValidatedField label="Datumeinde" id="informatiedakloosheid-datumeinde" name="datumeinde" data-cy="datumeinde" type="date" />
              <ValidatedField label="Datumstart" id="informatiedakloosheid-datumstart" name="datumstart" data-cy="datumstart" type="date" />
              <ValidatedField
                label="Gemeenteoorsprong"
                id="informatiedakloosheid-gemeenteoorsprong"
                name="gemeenteoorsprong"
                data-cy="gemeenteoorsprong"
                type="text"
              />
              <ValidatedField
                label="Toestemminggemeentelijkbriefadres"
                id="informatiedakloosheid-toestemminggemeentelijkbriefadres"
                name="toestemminggemeentelijkbriefadres"
                data-cy="toestemminggemeentelijkbriefadres"
                check
                type="checkbox"
              />
              <ValidatedField
                label="Toestemmingnachtopvang"
                id="informatiedakloosheid-toestemmingnachtopvang"
                name="toestemmingnachtopvang"
                data-cy="toestemmingnachtopvang"
                check
                type="checkbox"
              />
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/informatiedakloosheid" replace color="info">
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

export default InformatiedakloosheidUpdate;
