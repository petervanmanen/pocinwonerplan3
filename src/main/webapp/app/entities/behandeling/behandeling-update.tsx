import React, { useState, useEffect } from 'react';
import { Link, useNavigate, useParams } from 'react-router-dom';
import { Button, Row, Col, FormText } from 'reactstrap';
import { isNumber, ValidatedField, ValidatedForm } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { IBehandelsoort } from 'app/shared/model/behandelsoort.model';
import { getEntities as getBehandelsoorts } from 'app/entities/behandelsoort/behandelsoort.reducer';
import { IBehandeling } from 'app/shared/model/behandeling.model';
import { getEntity, updateEntity, createEntity, reset } from './behandeling.reducer';

export const BehandelingUpdate = () => {
  const dispatch = useAppDispatch();

  const navigate = useNavigate();

  const { id } = useParams<'id'>();
  const isNew = id === undefined;

  const behandelsoorts = useAppSelector(state => state.behandelsoort.entities);
  const behandelingEntity = useAppSelector(state => state.behandeling.entity);
  const loading = useAppSelector(state => state.behandeling.loading);
  const updating = useAppSelector(state => state.behandeling.updating);
  const updateSuccess = useAppSelector(state => state.behandeling.updateSuccess);

  const handleClose = () => {
    navigate('/behandeling');
  };

  useEffect(() => {
    if (isNew) {
      dispatch(reset());
    } else {
      dispatch(getEntity(id));
    }

    dispatch(getBehandelsoorts({}));
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
      ...behandelingEntity,
      ...values,
      isvansoortBehandelsoort: behandelsoorts.find(it => it.id.toString() === values.isvansoortBehandelsoort?.toString()),
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
          ...behandelingEntity,
          isvansoortBehandelsoort: behandelingEntity?.isvansoortBehandelsoort?.id,
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="demo3App.behandeling.home.createOrEditLabel" data-cy="BehandelingCreateUpdateHeading">
            Create or edit a Behandeling
          </h2>
        </Col>
      </Row>
      <Row className="justify-content-center">
        <Col md="8">
          {loading ? (
            <p>Loading...</p>
          ) : (
            <ValidatedForm defaultValues={defaultValues()} onSubmit={saveEntity}>
              {!isNew ? <ValidatedField name="id" required readOnly id="behandeling-id" label="ID" validate={{ required: true }} /> : null}
              <ValidatedField label="Datumeinde" id="behandeling-datumeinde" name="datumeinde" data-cy="datumeinde" type="text" />
              <ValidatedField label="Datumstart" id="behandeling-datumstart" name="datumstart" data-cy="datumstart" type="text" />
              <ValidatedField label="Toelichting" id="behandeling-toelichting" name="toelichting" data-cy="toelichting" type="text" />
              <ValidatedField
                id="behandeling-isvansoortBehandelsoort"
                name="isvansoortBehandelsoort"
                data-cy="isvansoortBehandelsoort"
                label="Isvansoort Behandelsoort"
                type="select"
              >
                <option value="" key="0" />
                {behandelsoorts
                  ? behandelsoorts.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.id}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/behandeling" replace color="info">
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

export default BehandelingUpdate;
