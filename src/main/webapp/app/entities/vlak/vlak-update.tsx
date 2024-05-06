import React, { useState, useEffect } from 'react';
import { Link, useNavigate, useParams } from 'react-router-dom';
import { Button, Row, Col, FormText } from 'reactstrap';
import { isNumber, ValidatedField, ValidatedForm } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { IPut } from 'app/shared/model/put.model';
import { getEntities as getPuts } from 'app/entities/put/put.reducer';
import { IVlak } from 'app/shared/model/vlak.model';
import { getEntity, updateEntity, createEntity, reset } from './vlak.reducer';

export const VlakUpdate = () => {
  const dispatch = useAppDispatch();

  const navigate = useNavigate();

  const { id } = useParams<'id'>();
  const isNew = id === undefined;

  const puts = useAppSelector(state => state.put.entities);
  const vlakEntity = useAppSelector(state => state.vlak.entity);
  const loading = useAppSelector(state => state.vlak.loading);
  const updating = useAppSelector(state => state.vlak.updating);
  const updateSuccess = useAppSelector(state => state.vlak.updateSuccess);

  const handleClose = () => {
    navigate('/vlak');
  };

  useEffect(() => {
    if (isNew) {
      dispatch(reset());
    } else {
      dispatch(getEntity(id));
    }

    dispatch(getPuts({}));
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
      ...vlakEntity,
      ...values,
      heeftPut: puts.find(it => it.id.toString() === values.heeftPut?.toString()),
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
          ...vlakEntity,
          heeftPut: vlakEntity?.heeftPut?.id,
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="demo3App.vlak.home.createOrEditLabel" data-cy="VlakCreateUpdateHeading">
            Create or edit a Vlak
          </h2>
        </Col>
      </Row>
      <Row className="justify-content-center">
        <Col md="8">
          {loading ? (
            <p>Loading...</p>
          ) : (
            <ValidatedForm defaultValues={defaultValues()} onSubmit={saveEntity}>
              {!isNew ? <ValidatedField name="id" required readOnly id="vlak-id" label="ID" validate={{ required: true }} /> : null}
              <ValidatedField label="Dieptetot" id="vlak-dieptetot" name="dieptetot" data-cy="dieptetot" type="text" />
              <ValidatedField label="Dieptevan" id="vlak-dieptevan" name="dieptevan" data-cy="dieptevan" type="text" />
              <ValidatedField label="Key" id="vlak-key" name="key" data-cy="key" type="text" />
              <ValidatedField label="Keyput" id="vlak-keyput" name="keyput" data-cy="keyput" type="text" />
              <ValidatedField
                label="Projectcd"
                id="vlak-projectcd"
                name="projectcd"
                data-cy="projectcd"
                type="text"
                validate={{
                  maxLength: { value: 20, message: 'This field cannot be longer than 20 characters.' },
                }}
              />
              <ValidatedField
                label="Putnummer"
                id="vlak-putnummer"
                name="putnummer"
                data-cy="putnummer"
                type="text"
                validate={{
                  maxLength: { value: 20, message: 'This field cannot be longer than 20 characters.' },
                }}
              />
              <ValidatedField
                label="Vlaknummer"
                id="vlak-vlaknummer"
                name="vlaknummer"
                data-cy="vlaknummer"
                type="text"
                validate={{
                  maxLength: { value: 20, message: 'This field cannot be longer than 20 characters.' },
                }}
              />
              <ValidatedField id="vlak-heeftPut" name="heeftPut" data-cy="heeftPut" label="Heeft Put" type="select">
                <option value="" key="0" />
                {puts
                  ? puts.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.id}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/vlak" replace color="info">
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

export default VlakUpdate;
