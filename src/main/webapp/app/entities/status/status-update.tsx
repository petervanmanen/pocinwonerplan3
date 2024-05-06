import React, { useState, useEffect } from 'react';
import { Link, useNavigate, useParams } from 'react-router-dom';
import { Button, Row, Col, FormText } from 'reactstrap';
import { isNumber, ValidatedField, ValidatedForm } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { IStatustype } from 'app/shared/model/statustype.model';
import { getEntities as getStatustypes } from 'app/entities/statustype/statustype.reducer';
import { IZaak } from 'app/shared/model/zaak.model';
import { getEntities as getZaaks } from 'app/entities/zaak/zaak.reducer';
import { IStatus } from 'app/shared/model/status.model';
import { getEntity, updateEntity, createEntity, reset } from './status.reducer';

export const StatusUpdate = () => {
  const dispatch = useAppDispatch();

  const navigate = useNavigate();

  const { id } = useParams<'id'>();
  const isNew = id === undefined;

  const statustypes = useAppSelector(state => state.statustype.entities);
  const zaaks = useAppSelector(state => state.zaak.entities);
  const statusEntity = useAppSelector(state => state.status.entity);
  const loading = useAppSelector(state => state.status.loading);
  const updating = useAppSelector(state => state.status.updating);
  const updateSuccess = useAppSelector(state => state.status.updateSuccess);

  const handleClose = () => {
    navigate('/status');
  };

  useEffect(() => {
    if (isNew) {
      dispatch(reset());
    } else {
      dispatch(getEntity(id));
    }

    dispatch(getStatustypes({}));
    dispatch(getZaaks({}));
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
      ...statusEntity,
      ...values,
      isvanStatustype: statustypes.find(it => it.id.toString() === values.isvanStatustype?.toString()),
      heeftZaak: zaaks.find(it => it.id.toString() === values.heeftZaak?.toString()),
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
          ...statusEntity,
          isvanStatustype: statusEntity?.isvanStatustype?.id,
          heeftZaak: statusEntity?.heeftZaak?.id,
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="demo3App.status.home.createOrEditLabel" data-cy="StatusCreateUpdateHeading">
            Create or edit a Status
          </h2>
        </Col>
      </Row>
      <Row className="justify-content-center">
        <Col md="8">
          {loading ? (
            <p>Loading...</p>
          ) : (
            <ValidatedForm defaultValues={defaultValues()} onSubmit={saveEntity}>
              {!isNew ? <ValidatedField name="id" required readOnly id="status-id" label="ID" validate={{ required: true }} /> : null}
              <ValidatedField
                label="Datumstatusgezet"
                id="status-datumstatusgezet"
                name="datumstatusgezet"
                data-cy="datumstatusgezet"
                type="text"
              />
              <ValidatedField
                label="Indicatieiaatstgezettestatus"
                id="status-indicatieiaatstgezettestatus"
                name="indicatieiaatstgezettestatus"
                data-cy="indicatieiaatstgezettestatus"
                type="text"
              />
              <ValidatedField
                label="Statustoelichting"
                id="status-statustoelichting"
                name="statustoelichting"
                data-cy="statustoelichting"
                type="text"
              />
              <ValidatedField
                id="status-isvanStatustype"
                name="isvanStatustype"
                data-cy="isvanStatustype"
                label="Isvan Statustype"
                type="select"
              >
                <option value="" key="0" />
                {statustypes
                  ? statustypes.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.id}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <ValidatedField id="status-heeftZaak" name="heeftZaak" data-cy="heeftZaak" label="Heeft Zaak" type="select">
                <option value="" key="0" />
                {zaaks
                  ? zaaks.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.id}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/status" replace color="info">
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

export default StatusUpdate;
