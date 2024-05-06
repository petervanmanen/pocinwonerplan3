import React, { useState, useEffect } from 'react';
import { Link, useNavigate, useParams } from 'react-router-dom';
import { Button, Row, Col, FormText } from 'reactstrap';
import { isNumber, ValidatedField, ValidatedForm } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { IRechtspersoon } from 'app/shared/model/rechtspersoon.model';
import { getEntities as getRechtspersoons } from 'app/entities/rechtspersoon/rechtspersoon.reducer';
import { IVastgoedcontract } from 'app/shared/model/vastgoedcontract.model';
import { getEntity, updateEntity, createEntity, reset } from './vastgoedcontract.reducer';

export const VastgoedcontractUpdate = () => {
  const dispatch = useAppDispatch();

  const navigate = useNavigate();

  const { id } = useParams<'id'>();
  const isNew = id === undefined;

  const rechtspersoons = useAppSelector(state => state.rechtspersoon.entities);
  const vastgoedcontractEntity = useAppSelector(state => state.vastgoedcontract.entity);
  const loading = useAppSelector(state => state.vastgoedcontract.loading);
  const updating = useAppSelector(state => state.vastgoedcontract.updating);
  const updateSuccess = useAppSelector(state => state.vastgoedcontract.updateSuccess);

  const handleClose = () => {
    navigate('/vastgoedcontract');
  };

  useEffect(() => {
    if (isNew) {
      dispatch(reset());
    } else {
      dispatch(getEntity(id));
    }

    dispatch(getRechtspersoons({}));
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
    if (values.maandbedrag !== undefined && typeof values.maandbedrag !== 'number') {
      values.maandbedrag = Number(values.maandbedrag);
    }

    const entity = {
      ...vastgoedcontractEntity,
      ...values,
      heeftRechtspersoon: rechtspersoons.find(it => it.id.toString() === values.heeftRechtspersoon?.toString()),
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
          ...vastgoedcontractEntity,
          heeftRechtspersoon: vastgoedcontractEntity?.heeftRechtspersoon?.id,
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="demo3App.vastgoedcontract.home.createOrEditLabel" data-cy="VastgoedcontractCreateUpdateHeading">
            Create or edit a Vastgoedcontract
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
                <ValidatedField name="id" required readOnly id="vastgoedcontract-id" label="ID" validate={{ required: true }} />
              ) : null}
              <ValidatedField
                label="Beschrijving"
                id="vastgoedcontract-beschrijving"
                name="beschrijving"
                data-cy="beschrijving"
                type="text"
              />
              <ValidatedField label="Datumeinde" id="vastgoedcontract-datumeinde" name="datumeinde" data-cy="datumeinde" type="date" />
              <ValidatedField label="Datumstart" id="vastgoedcontract-datumstart" name="datumstart" data-cy="datumstart" type="date" />
              <ValidatedField
                label="Identificatie"
                id="vastgoedcontract-identificatie"
                name="identificatie"
                data-cy="identificatie"
                type="text"
              />
              <ValidatedField label="Maandbedrag" id="vastgoedcontract-maandbedrag" name="maandbedrag" data-cy="maandbedrag" type="text" />
              <ValidatedField
                label="Opzegtermijn"
                id="vastgoedcontract-opzegtermijn"
                name="opzegtermijn"
                data-cy="opzegtermijn"
                type="text"
              />
              <ValidatedField label="Status" id="vastgoedcontract-status" name="status" data-cy="status" type="text" />
              <ValidatedField label="Type" id="vastgoedcontract-type" name="type" data-cy="type" type="text" />
              <ValidatedField
                id="vastgoedcontract-heeftRechtspersoon"
                name="heeftRechtspersoon"
                data-cy="heeftRechtspersoon"
                label="Heeft Rechtspersoon"
                type="select"
              >
                <option value="" key="0" />
                {rechtspersoons
                  ? rechtspersoons.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.id}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/vastgoedcontract" replace color="info">
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

export default VastgoedcontractUpdate;
