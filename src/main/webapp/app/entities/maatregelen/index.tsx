import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import Maatregelen from './maatregelen';
import MaatregelenDetail from './maatregelen-detail';
import MaatregelenUpdate from './maatregelen-update';
import MaatregelenDeleteDialog from './maatregelen-delete-dialog';

const MaatregelenRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<Maatregelen />} />
    <Route path="new" element={<MaatregelenUpdate />} />
    <Route path=":id">
      <Route index element={<MaatregelenDetail />} />
      <Route path="edit" element={<MaatregelenUpdate />} />
      <Route path="delete" element={<MaatregelenDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default MaatregelenRoutes;
