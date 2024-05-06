import React, { useState, useEffect } from 'react';
import { Link, useNavigate, useParams } from 'react-router-dom';
import { Button, Row, Col, FormText } from 'reactstrap';
import { isNumber, ValidatedField, ValidatedForm } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { IHeffingsverordening } from 'app/shared/model/heffingsverordening.model';
import { getEntities as getHeffingsverordenings } from 'app/entities/heffingsverordening/heffingsverordening.reducer';
import { IZaaktype } from 'app/shared/model/zaaktype.model';
import { getEntities as getZaaktypes } from 'app/entities/zaaktype/zaaktype.reducer';
import { IHeffinggrondslag } from 'app/shared/model/heffinggrondslag.model';
import { getEntity, updateEntity, createEntity, reset } from './heffinggrondslag.reducer';

export const HeffinggrondslagUpdate = () => {
  const dispatch = useAppDispatch();

  const navigate = useNavigate();

  const { id } = useParams<'id'>();
  const isNew = id === undefined;

  const heffingsverordenings = useAppSelector(state => state.heffingsverordening.entities);
  const zaaktypes = useAppSelector(state => state.zaaktype.entities);
  const heffinggrondslagEntity = useAppSelector(state => state.heffinggrondslag.entity);
  const loading = useAppSelector(state => state.heffinggrondslag.loading);
  const updating = useAppSelector(state => state.heffinggrondslag.updating);
  const updateSuccess = useAppSelector(state => state.heffinggrondslag.updateSuccess);

  const handleClose = () => {
    navigate('/heffinggrondslag');
  };

  useEffect(() => {
    if (isNew) {
      dispatch(reset());
    } else {
      dispatch(getEntity(id));
    }

    dispatch(getHeffingsverordenings({}));
    dispatch(getZaaktypes({}));
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
    if (values.bedrag !== undefined && typeof values.bedrag !== 'number') {
      values.bedrag = Number(values.bedrag);
    }

    const entity = {
      ...heffinggrondslagEntity,
      ...values,
      vermeldinHeffingsverordening: heffingsverordenings.find(it => it.id.toString() === values.vermeldinHeffingsverordening?.toString()),
      heeftZaaktype: zaaktypes.find(it => it.id.toString() === values.heeftZaaktype?.toString()),
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
          ...heffinggrondslagEntity,
          vermeldinHeffingsverordening: heffinggrondslagEntity?.vermeldinHeffingsverordening?.id,
          heeftZaaktype: heffinggrondslagEntity?.heeftZaaktype?.id,
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="demo3App.heffinggrondslag.home.createOrEditLabel" data-cy="HeffinggrondslagCreateUpdateHeading">
            Create or edit a Heffinggrondslag
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
                <ValidatedField name="id" required readOnly id="heffinggrondslag-id" label="ID" validate={{ required: true }} />
              ) : null}
              <ValidatedField label="Bedrag" id="heffinggrondslag-bedrag" name="bedrag" data-cy="bedrag" type="text" />
              <ValidatedField label="Domein" id="heffinggrondslag-domein" name="domein" data-cy="domein" type="text" />
              <ValidatedField label="Hoofdstuk" id="heffinggrondslag-hoofdstuk" name="hoofdstuk" data-cy="hoofdstuk" type="text" />
              <ValidatedField
                label="Omschrijving"
                id="heffinggrondslag-omschrijving"
                name="omschrijving"
                data-cy="omschrijving"
                type="text"
              />
              <ValidatedField
                label="Paragraaf"
                id="heffinggrondslag-paragraaf"
                name="paragraaf"
                data-cy="paragraaf"
                type="text"
                validate={{
                  maxLength: { value: 8, message: 'This field cannot be longer than 8 characters.' },
                }}
              />
              <ValidatedField
                id="heffinggrondslag-vermeldinHeffingsverordening"
                name="vermeldinHeffingsverordening"
                data-cy="vermeldinHeffingsverordening"
                label="Vermeldin Heffingsverordening"
                type="select"
              >
                <option value="" key="0" />
                {heffingsverordenings
                  ? heffingsverordenings.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.id}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <ValidatedField
                id="heffinggrondslag-heeftZaaktype"
                name="heeftZaaktype"
                data-cy="heeftZaaktype"
                label="Heeft Zaaktype"
                type="select"
              >
                <option value="" key="0" />
                {zaaktypes
                  ? zaaktypes.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.id}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/heffinggrondslag" replace color="info">
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

export default HeffinggrondslagUpdate;
