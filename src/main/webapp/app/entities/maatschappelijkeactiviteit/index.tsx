import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import Maatschappelijkeactiviteit from './maatschappelijkeactiviteit';
import MaatschappelijkeactiviteitDetail from './maatschappelijkeactiviteit-detail';
import MaatschappelijkeactiviteitUpdate from './maatschappelijkeactiviteit-update';
import MaatschappelijkeactiviteitDeleteDialog from './maatschappelijkeactiviteit-delete-dialog';

const MaatschappelijkeactiviteitRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<Maatschappelijkeactiviteit />} />
    <Route path="new" element={<MaatschappelijkeactiviteitUpdate />} />
    <Route path=":id">
      <Route index element={<MaatschappelijkeactiviteitDetail />} />
      <Route path="edit" element={<MaatschappelijkeactiviteitUpdate />} />
      <Route path="delete" element={<MaatschappelijkeactiviteitDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default MaatschappelijkeactiviteitRoutes;
