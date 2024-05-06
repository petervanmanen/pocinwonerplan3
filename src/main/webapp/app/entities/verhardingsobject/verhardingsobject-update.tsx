import React, { useState, useEffect } from 'react';
import { Link, useNavigate, useParams } from 'react-router-dom';
import { Button, Row, Col, FormText } from 'reactstrap';
import { isNumber, ValidatedField, ValidatedForm } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { IVerhardingsobject } from 'app/shared/model/verhardingsobject.model';
import { getEntity, updateEntity, createEntity, reset } from './verhardingsobject.reducer';

export const VerhardingsobjectUpdate = () => {
  const dispatch = useAppDispatch();

  const navigate = useNavigate();

  const { id } = useParams<'id'>();
  const isNew = id === undefined;

  const verhardingsobjectEntity = useAppSelector(state => state.verhardingsobject.entity);
  const loading = useAppSelector(state => state.verhardingsobject.loading);
  const updating = useAppSelector(state => state.verhardingsobject.updating);
  const updateSuccess = useAppSelector(state => state.verhardingsobject.updateSuccess);

  const handleClose = () => {
    navigate('/verhardingsobject');
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
      ...verhardingsobjectEntity,
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
          ...verhardingsobjectEntity,
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="demo3App.verhardingsobject.home.createOrEditLabel" data-cy="VerhardingsobjectCreateUpdateHeading">
            Create or edit a Verhardingsobject
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
                <ValidatedField name="id" required readOnly id="verhardingsobject-id" label="ID" validate={{ required: true }} />
              ) : null}
              <ValidatedField
                label="Aanleghoogte"
                id="verhardingsobject-aanleghoogte"
                name="aanleghoogte"
                data-cy="aanleghoogte"
                type="text"
              />
              <ValidatedField
                label="Aanofvrijliggend"
                id="verhardingsobject-aanofvrijliggend"
                name="aanofvrijliggend"
                data-cy="aanofvrijliggend"
                type="text"
              />
              <ValidatedField
                label="Aantaldeklagen"
                id="verhardingsobject-aantaldeklagen"
                name="aantaldeklagen"
                data-cy="aantaldeklagen"
                type="text"
              />
              <ValidatedField
                label="Aantalonderlagen"
                id="verhardingsobject-aantalonderlagen"
                name="aantalonderlagen"
                data-cy="aantalonderlagen"
                type="text"
              />
              <ValidatedField
                label="Aantaltussenlagen"
                id="verhardingsobject-aantaltussenlagen"
                name="aantaltussenlagen"
                data-cy="aantaltussenlagen"
                type="text"
              />
              <ValidatedField label="Afmeting" id="verhardingsobject-afmeting" name="afmeting" data-cy="afmeting" type="text" />
              <ValidatedField label="Belasting" id="verhardingsobject-belasting" name="belasting" data-cy="belasting" type="text" />
              <ValidatedField
                label="Bergendvermogen"
                id="verhardingsobject-bergendvermogen"
                name="bergendvermogen"
                data-cy="bergendvermogen"
                type="text"
              />
              <ValidatedField
                label="Bgtfysiekvoorkomen"
                id="verhardingsobject-bgtfysiekvoorkomen"
                name="bgtfysiekvoorkomen"
                data-cy="bgtfysiekvoorkomen"
                type="text"
              />
              <ValidatedField label="Breedte" id="verhardingsobject-breedte" name="breedte" data-cy="breedte" type="text" />
              <ValidatedField
                label="Dikteconstructie"
                id="verhardingsobject-dikteconstructie"
                name="dikteconstructie"
                data-cy="dikteconstructie"
                type="text"
              />
              <ValidatedField
                label="Draagkrachtig"
                id="verhardingsobject-draagkrachtig"
                name="draagkrachtig"
                data-cy="draagkrachtig"
                check
                type="checkbox"
              />
              <ValidatedField label="Formaat" id="verhardingsobject-formaat" name="formaat" data-cy="formaat" type="text" />
              <ValidatedField
                label="Fysiekvoorkomenimgeo"
                id="verhardingsobject-fysiekvoorkomenimgeo"
                name="fysiekvoorkomenimgeo"
                data-cy="fysiekvoorkomenimgeo"
                type="text"
              />
              <ValidatedField
                label="Geluidsreducerend"
                id="verhardingsobject-geluidsreducerend"
                name="geluidsreducerend"
                data-cy="geluidsreducerend"
                check
                type="checkbox"
              />
              <ValidatedField
                label="Jaarconserveren"
                id="verhardingsobject-jaarconserveren"
                name="jaarconserveren"
                data-cy="jaarconserveren"
                type="text"
              />
              <ValidatedField
                label="Jaaronderhouduitgevoerd"
                id="verhardingsobject-jaaronderhouduitgevoerd"
                name="jaaronderhouduitgevoerd"
                data-cy="jaaronderhouduitgevoerd"
                type="text"
              />
              <ValidatedField
                label="Jaarpraktischeinde"
                id="verhardingsobject-jaarpraktischeinde"
                name="jaarpraktischeinde"
                data-cy="jaarpraktischeinde"
                type="text"
              />
              <ValidatedField label="Kleur" id="verhardingsobject-kleur" name="kleur" data-cy="kleur" type="text" />
              <ValidatedField
                label="Kwaliteitsniveauactueel"
                id="verhardingsobject-kwaliteitsniveauactueel"
                name="kwaliteitsniveauactueel"
                data-cy="kwaliteitsniveauactueel"
                type="text"
              />
              <ValidatedField
                label="Kwaliteitsniveaugewenst"
                id="verhardingsobject-kwaliteitsniveaugewenst"
                name="kwaliteitsniveaugewenst"
                data-cy="kwaliteitsniveaugewenst"
                type="text"
              />
              <ValidatedField label="Lengte" id="verhardingsobject-lengte" name="lengte" data-cy="lengte" type="text" />
              <ValidatedField
                label="Lengtekunstgras"
                id="verhardingsobject-lengtekunstgras"
                name="lengtekunstgras"
                data-cy="lengtekunstgras"
                type="text"
              />
              <ValidatedField
                label="Lengtevoegen"
                id="verhardingsobject-lengtevoegen"
                name="lengtevoegen"
                data-cy="lengtevoegen"
                type="text"
              />
              <ValidatedField label="Levensduur" id="verhardingsobject-levensduur" name="levensduur" data-cy="levensduur" type="text" />
              <ValidatedField label="Materiaal" id="verhardingsobject-materiaal" name="materiaal" data-cy="materiaal" type="text" />
              <ValidatedField
                label="Maximalevalhoogte"
                id="verhardingsobject-maximalevalhoogte"
                name="maximalevalhoogte"
                data-cy="maximalevalhoogte"
                type="text"
              />
              <ValidatedField label="Omtrek" id="verhardingsobject-omtrek" name="omtrek" data-cy="omtrek" type="text" />
              <ValidatedField
                label="Ondergrondcode"
                id="verhardingsobject-ondergrondcode"
                name="ondergrondcode"
                data-cy="ondergrondcode"
                type="text"
                validate={{
                  maxLength: { value: 10, message: 'This field cannot be longer than 10 characters.' },
                }}
              />
              <ValidatedField label="Oppervlakte" id="verhardingsobject-oppervlakte" name="oppervlakte" data-cy="oppervlakte" type="text" />
              <ValidatedField label="Optalud" id="verhardingsobject-optalud" name="optalud" data-cy="optalud" type="text" />
              <ValidatedField
                label="Plaatsorientatie"
                id="verhardingsobject-plaatsorientatie"
                name="plaatsorientatie"
                data-cy="plaatsorientatie"
                type="text"
              />
              <ValidatedField
                label="Prijsaanschaf"
                id="verhardingsobject-prijsaanschaf"
                name="prijsaanschaf"
                data-cy="prijsaanschaf"
                type="text"
              />
              <ValidatedField label="Rijstrook" id="verhardingsobject-rijstrook" name="rijstrook" data-cy="rijstrook" type="text" />
              <ValidatedField label="Soortvoeg" id="verhardingsobject-soortvoeg" name="soortvoeg" data-cy="soortvoeg" type="text" />
              <ValidatedField
                label="Toelichtinggemengdebestrating"
                id="verhardingsobject-toelichtinggemengdebestrating"
                name="toelichtinggemengdebestrating"
                data-cy="toelichtinggemengdebestrating"
                type="text"
              />
              <ValidatedField label="Type" id="verhardingsobject-type" name="type" data-cy="type" type="text" />
              <ValidatedField
                label="Typeconstructie"
                id="verhardingsobject-typeconstructie"
                name="typeconstructie"
                data-cy="typeconstructie"
                type="text"
              />
              <ValidatedField
                label="Typefundering"
                id="verhardingsobject-typefundering"
                name="typefundering"
                data-cy="typefundering"
                type="text"
              />
              <ValidatedField label="Typeplus" id="verhardingsobject-typeplus" name="typeplus" data-cy="typeplus" type="text" />
              <ValidatedField
                label="Typeplus 2"
                id="verhardingsobject-typeplus2"
                name="typeplus2"
                data-cy="typeplus2"
                type="text"
                validate={{
                  maxLength: { value: 100, message: 'This field cannot be longer than 100 characters.' },
                }}
              />
              <ValidatedField
                label="Typerijstrook"
                id="verhardingsobject-typerijstrook"
                name="typerijstrook"
                data-cy="typerijstrook"
                type="text"
              />
              <ValidatedField label="Typevoeg" id="verhardingsobject-typevoeg" name="typevoeg" data-cy="typevoeg" type="text" />
              <ValidatedField
                label="Typevoegvulling"
                id="verhardingsobject-typevoegvulling"
                name="typevoegvulling"
                data-cy="typevoegvulling"
                type="text"
              />
              <ValidatedField label="Vegen" id="verhardingsobject-vegen" name="vegen" data-cy="vegen" type="text" />
              <ValidatedField
                label="Verhardingsobjectconstructielaag"
                id="verhardingsobject-verhardingsobjectconstructielaag"
                name="verhardingsobjectconstructielaag"
                data-cy="verhardingsobjectconstructielaag"
                type="text"
              />
              <ValidatedField
                label="Verhardingsobjectmodaliteit"
                id="verhardingsobject-verhardingsobjectmodaliteit"
                name="verhardingsobjectmodaliteit"
                data-cy="verhardingsobjectmodaliteit"
                type="text"
              />
              <ValidatedField
                label="Verhardingsobjectrand"
                id="verhardingsobject-verhardingsobjectrand"
                name="verhardingsobjectrand"
                data-cy="verhardingsobjectrand"
                type="text"
              />
              <ValidatedField
                label="Verhardingsobjectwegfunctie"
                id="verhardingsobject-verhardingsobjectwegfunctie"
                name="verhardingsobjectwegfunctie"
                data-cy="verhardingsobjectwegfunctie"
                type="text"
              />
              <ValidatedField
                label="Verhoogdeligging"
                id="verhardingsobject-verhoogdeligging"
                name="verhoogdeligging"
                data-cy="verhoogdeligging"
                check
                type="checkbox"
              />
              <ValidatedField
                label="Vulmateriaalkunstgras"
                id="verhardingsobject-vulmateriaalkunstgras"
                name="vulmateriaalkunstgras"
                data-cy="vulmateriaalkunstgras"
                type="text"
              />
              <ValidatedField
                label="Waterdoorlatendheid"
                id="verhardingsobject-waterdoorlatendheid"
                name="waterdoorlatendheid"
                data-cy="waterdoorlatendheid"
                type="text"
              />
              <ValidatedField label="Wegas" id="verhardingsobject-wegas" name="wegas" data-cy="wegas" type="text" />
              <ValidatedField
                label="Wegcategoriedv"
                id="verhardingsobject-wegcategoriedv"
                name="wegcategoriedv"
                data-cy="wegcategoriedv"
                type="text"
              />
              <ValidatedField
                label="Wegcategoriedvplus"
                id="verhardingsobject-wegcategoriedvplus"
                name="wegcategoriedvplus"
                data-cy="wegcategoriedvplus"
                type="text"
              />
              <ValidatedField
                label="Wegnummer"
                id="verhardingsobject-wegnummer"
                name="wegnummer"
                data-cy="wegnummer"
                type="text"
                validate={{
                  maxLength: { value: 20, message: 'This field cannot be longer than 20 characters.' },
                }}
              />
              <ValidatedField
                label="Wegtypebestaand"
                id="verhardingsobject-wegtypebestaand"
                name="wegtypebestaand"
                data-cy="wegtypebestaand"
                type="text"
              />
              <ValidatedField label="Wegvak" id="verhardingsobject-wegvak" name="wegvak" data-cy="wegvak" type="text" />
              <ValidatedField
                label="Wegvaknummer"
                id="verhardingsobject-wegvaknummer"
                name="wegvaknummer"
                data-cy="wegvaknummer"
                type="text"
                validate={{
                  maxLength: { value: 20, message: 'This field cannot be longer than 20 characters.' },
                }}
              />
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/verhardingsobject" replace color="info">
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

export default VerhardingsobjectUpdate;
