import React, { useState, useEffect } from 'react';
import { Link, useNavigate, useParams } from 'react-router-dom';
import { Button, Row, Col, FormText } from 'reactstrap';
import { isNumber, ValidatedField, ValidatedForm } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { IWozdeelobject } from 'app/shared/model/wozdeelobject.model';
import { getEntity, updateEntity, createEntity, reset } from './wozdeelobject.reducer';

export const WozdeelobjectUpdate = () => {
  const dispatch = useAppDispatch();

  const navigate = useNavigate();

  const { id } = useParams<'id'>();
  const isNew = id === undefined;

  const wozdeelobjectEntity = useAppSelector(state => state.wozdeelobject.entity);
  const loading = useAppSelector(state => state.wozdeelobject.loading);
  const updating = useAppSelector(state => state.wozdeelobject.updating);
  const updateSuccess = useAppSelector(state => state.wozdeelobject.updateSuccess);

  const handleClose = () => {
    navigate('/wozdeelobject');
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
      ...wozdeelobjectEntity,
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
          ...wozdeelobjectEntity,
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="demo3App.wozdeelobject.home.createOrEditLabel" data-cy="WozdeelobjectCreateUpdateHeading">
            Create or edit a Wozdeelobject
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
                <ValidatedField name="id" required readOnly id="wozdeelobject-id" label="ID" validate={{ required: true }} />
              ) : null}
              <ValidatedField
                label="Codewozdeelobject"
                id="wozdeelobject-codewozdeelobject"
                name="codewozdeelobject"
                data-cy="codewozdeelobject"
                type="text"
              />
              <ValidatedField
                label="Datumbegingeldigheiddeelobject"
                id="wozdeelobject-datumbegingeldigheiddeelobject"
                name="datumbegingeldigheiddeelobject"
                data-cy="datumbegingeldigheiddeelobject"
                type="date"
              />
              <ValidatedField
                label="Datumeindegeldigheiddeelobject"
                id="wozdeelobject-datumeindegeldigheiddeelobject"
                name="datumeindegeldigheiddeelobject"
                data-cy="datumeindegeldigheiddeelobject"
                type="date"
              />
              <ValidatedField
                label="Statuswozdeelobject"
                id="wozdeelobject-statuswozdeelobject"
                name="statuswozdeelobject"
                data-cy="statuswozdeelobject"
                type="text"
              />
              <ValidatedField
                label="Wozdeelobjectnummer"
                id="wozdeelobject-wozdeelobjectnummer"
                name="wozdeelobjectnummer"
                data-cy="wozdeelobjectnummer"
                type="text"
              />
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/wozdeelobject" replace color="info">
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

export default WozdeelobjectUpdate;
